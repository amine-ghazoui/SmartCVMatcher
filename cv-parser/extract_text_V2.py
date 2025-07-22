import fitz  # PyMuPDF
import requests
import json
import os

# === Configuration ===
GROQ_API_KEY = "GROQ_API_KEY"  # 🔐 Remplace par ta clé
GROQ_ENDPOINT = "https://api.groq.com/openai/v1/chat/completions"
MODEL_NAME = "gemma2-9b-it"

# === Étape 1 : Extraction du texte avec PyMuPDF ===
def extract_text_with_pymupdf(pdf_path):
    doc = fitz.open(pdf_path)
    text = ""
    for page in doc:
        text += page.get_text()
    return text

# === Étape 2 : Création du prompt ===
def build_prompt(cv_text):
    return f"""
Tu es un expert en extraction d'informations depuis les CVs. Voici un exemple de structure JSON à respecter :

{{
  "nom": "...",
  "titre": "...",
  "email": "...",
  "téléphone": "...",
  "profil": "...",
  "competences": ["...", "..."],
  "experiences": [
    {{
      "entreprise": "...",
      "description": "...",
      "outils": ["...", "..."]
    }}
  ],
  "projets": [
    {{
      "titre": "...",
      "description": "...",
      "outils": ["...", "..."]
    }}
  ],
  "diplômes": ["..."],
  "langues": {{
    "français": "courant",
    "anglais": "débutant"
  }}
}}

Voici le contenu du CV à analyser :
\"\"\"
{cv_text}
\"\"\"

Donne uniquement le JSON demandé, sans aucune explication.
"""

# === Étape 3 : Envoi à l'API Kimi K2 (via Groq) ===
def send_to_kimi(prompt):
    headers = {
        "Authorization": f"Bearer {GROQ_API_KEY}",
        "Content-Type": "application/json"
    }

    data = {
        "model": MODEL_NAME,
        "messages": [
            {"role": "system", "content": "Tu es un assistant intelligent spécialisé dans l'analyse de CVs."},
            {"role": "user", "content": prompt}
        ],
        "temperature": 0.2
    }

    response = requests.post(GROQ_ENDPOINT, headers=headers, json=data)
    response.raise_for_status()
    return response.json()["choices"][0]["message"]["content"]

# === Étape 4 : Pipeline principal ===
def process_cv(pdf_path):
    text = extract_text_with_pymupdf(pdf_path)
    prompt = build_prompt(text)
    response = send_to_kimi(prompt)

    try:
        return json.loads(response)
    except json.JSONDecodeError:
        print("❌ Erreur : réponse non JSON. Voici la réponse brute :\n")
        print(response)
        return None

# === Exemple d'utilisation ===
if __name__ == "__main__":
    dossier_cvs = "cv-downloaded"  # Chemin du dossier contenant les CVs
    resultats = []

    # Parcours tous les fichiers PDF du dossier
    for nom_fichier in os.listdir(dossier_cvs):
        if nom_fichier.lower().endswith(".pdf"):
            chemin_cv = os.path.join(dossier_cvs, nom_fichier)
            print(f"Traitement de : {chemin_cv}")
            resultat = process_cv(chemin_cv)
            if resultat:
                resultats.append(resultat)
                # Sauvegarde chaque résultat dans un fichier séparé
                nom_json = nom_fichier.replace(".pdf", ".json")
                with open(f"resultat_{nom_json}", "w", encoding="utf-8") as f:
                    json.dump(resultat, f, indent=2, ensure_ascii=False)
            else:
                print(f"Erreur pour le fichier : {chemin_cv}")

    # Sauvegarde tous les résultats dans un seul fichier si tu veux
    with open("tous_les_resultats.json", "w", encoding="utf-8") as f:
        json.dump(resultats, f, indent=2, ensure_ascii=False)

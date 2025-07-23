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
  "telephone": "...",
  "profil": "...",
  "competences": [{{ "nom": "..." }}, {{ "nom": "..." }}],
  "experiences": [
    {{
      "entreprise": "...",
      "description": "...",
      "technologie": "..."
    }}
  ],
  "projets": [
    {{
      "titre": "...",
      "description": "...",
      "technologie": "..."
    }}
  ],
  "diplomes": [{{ "intitule": "..." }}, {{ "intitule": "..." }}],
  "langues": [{{ "nom": "..." }}, {{ "nom": "..." }}]
}}

**Consignes importantes :**
- Donne uniquement le JSON brut, sans aucune balise ``` ou ```json autour, ni explication.
- Pour 'langues', donne une liste d’objets, chacun sous la forme {{ "nom": "..." }} (et non une liste de chaînes).
- Pour le champ 'technologie' dans 'experiences' et 'projets', donne toujours une chaîne de caractères (jamais une liste), même s'il y a plusieurs technologies : sépare-les par une virgule et un espace (exemple : "Java, Spring Boot, Angular").
- Résume le champ 'profil' en 2 à 3 phrases maximum, en allant à l'essentiel.
- Pour chaque 'description' (expériences et projets), fais un résumé très court (1 à 2 phrases maximum).
- Pour 'competences', liste toutes les compétences et outils mentionnés, même s'ils sont secondaires.
- Pour 'langues', donne uniquement la liste des langues, sans indiquer le niveau.

Voici le contenu du CV à analyser :
{cv_text}
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
SPRING_BOOT_URL = "http://localhost:8080/api/cvs"  # À adapter si besoin

def envoyer_au_backend(json_cv):
    try:
        response = requests.post(SPRING_BOOT_URL, json=json_cv)
        response.raise_for_status()
        print(f"✅ Envoyé au backend : {response.text}")
    except Exception as e:
        print(f"❌ Erreur lors de l'envoi au backend : {e}")

if __name__ == "__main__":
    dossier_cvs = "cv-parser/cv-downloaded"  # Chemin du dossier contenant les CVs
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
                envoyer_au_backend(resultat)
            else:
                print(f"Erreur pour le fichier : {chemin_cv}")

    # Sauvegarde tous les résultats dans un seul fichier si tu veux
    with open("tous_les_resultats.json", "w", encoding="utf-8") as f:
        json.dump(resultats, f, indent=2, ensure_ascii=False)

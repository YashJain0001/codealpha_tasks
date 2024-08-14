import random
import nltk
import spacy
from nltk.tokenize import word_tokenize
from nltk.corpus import wordnet
from nltk.corpus import stopwords
from nltk.stem import WordNetLemmatizer

# Download NLTK resources
nltk.download('punkt')
nltk.download('wordnet')
nltk.download('stopwords')

# Initialize the spacy NLP model and NLTK tools
nlp = spacy.load('en_core_web_sm')
lemmatizer = WordNetLemmatizer()

# Preprocessing function
def preprocess(text):
    tokens = word_tokenize(text.lower())
    tokens = [lemmatizer.lemmatize(word) for word in tokens if word not in stopwords.words('english')]
    return tokens

# Knowledge base
responses = {
    "greeting": ["Hello! How can I help you today?", "Hi there! What can I do for you?", "Hey! What's up?"],
    "goodbye": ["Goodbye! Have a great day!", "See you later!", "Take care!"],
    "thanks": ["You're welcome!", "No problem!", "Anytime!"],
    "default": ["I'm sorry, I didn't understand that.", "Can you please rephrase?", "I'm not sure I follow."]
}

keywords = {
    "greeting": ["hello", "hi", "hey", "morning", "afternoon"],
    "goodbye": ["bye", "goodbye", "see you", "later"],
    "thanks": ["thank", "thanks", "appreciate"]
}

# Match response
def match_response(user_input):
    tokens = preprocess(user_input)
    
    for intent, keys in keywords.items():
        if any(token in keys for token in tokens):
            return random.choice(responses[intent])
    
    return random.choice(responses["default"])

# Chatbot interface
def chatbot():
    print("Chatbot: Hello! I'm here to help. Type 'exit' to end the conversation.")
    
    while True:
        user_input = input("You: ")
        
        if user_input.lower() == 'exit':
            print("Chatbot: Goodbye!")
            break
        
        response = match_response(user_input)
        print(f"Chatbot: {response}")

if __name__ == "__main__":
    chatbot()

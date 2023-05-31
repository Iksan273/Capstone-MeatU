from flask import Flask, jsonify, request, json
from flask_sqlalchemy import SQLAlchemy
from flask_mysqldb import MySQL
import bcrypt
from flask_marshmallow import Marshmallow
app = Flask(__name__)

db = MySQL.connect(
    host = 'localhost',
    user = 'root',
    password = 'password',
    database = 'meatu'
)

@app.route('/login', methods=['POST'])
def login():
    data = request.get_json()
    email = data['email']
    password = data['password']

    cursor = db.cursor()    
    


if __name__ == "__main__":
    app.run()
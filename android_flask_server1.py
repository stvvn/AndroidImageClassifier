import flask

# Create a Flask app using an instance of Flask() class.
app = flask.Flask(__name__)

# app.route() decorator function associates URL with callback function
# URL entered is "/", refers to homepage of server (Android app requests
# sent to hompeage of server). Android app is sending a messge of type
# POST, thus, the method of type POST must be supported.
@app.route('/', methods= ['GET', 'POST'])
def handle_request():
    return "Flask Server & Android Working Successfully"

# run() method specify IPv4 address and port of server
# Setting debug to True enables server to restart itself
# for each change in source code
app.run(host="0.0.0.0", port=9504, debug= True)

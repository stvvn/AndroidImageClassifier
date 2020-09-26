import flask
import werkzeug
import numpy
from keras import models
from keras.preprocessing import image
import numpy as np

app = flask.Flask(__name__)

def predict_face_shape(img_path, model_path):
  
  # Based on what the model predicts, it returns the face Shape as a String
  prediction_mapping = {0:'Heart', 1:'Oblong', 2:'Oval', 3:'Round', 4:'Square'}

  # Load and preprocess the image for the model 
  img = image.load_img(img_path, target_size=(150, 150))
  img_tensor = image.img_to_array(img)
  img_tensor = np.expand_dims(img_tensor, axis=0)
  img_tensor /= 255.
  imgs = np.vstack([img_tensor])

  # Load the model and predict
  loaded_model = models.load_model(model_path)
  classes = loaded_model.predict_classes(imgs)

  # Return the prediction
  return prediction_mapping[classes[0]]


@app.route('/', methods = ['GET', 'POST'])
def handle_request():
    imagefile = flask.request.files['image']
    filename = werkzeug.utils.secure_filename(imagefile.filename)
    print("\nReceived image File name : " + imagefile.filename)
    imagefile.save(filename)
    
    img_path = filename
    # Put location of the file path
    model_path = "faceshapeone_vgg16_augment.h5"

    predicted_face_shape = predict_face_shape(img_path, model_path)
    print("Predicted Face Shape: ", predicted_face_shape)

    return str(predicted_face_shape)
       
app.run(host="0.0.0.0", port=5000, debug=True)

'''
if __name__ == '__main__':
    # Put location of the file path
    model_path = "faceshapeone_vgg16_augment.h5"
    filenames = ["round_" + str(i) for i in range(1,4)]

    print("Testing Round Face:")
    for filename in filenames:
        predicted_face_shape = predict_face_shape(filename, model_path)
        print("Predicted Face Shape: " + predicted_face_shape) 
'''

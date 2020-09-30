import flask
import werkzeug
import time
import numpy as np
from keras import models
from keras.preprocessing import image


app = flask.Flask(__name__)


def predict_face_shape(img_path, model_path):
    # Based on what the model predicts, it returns the face Shape as a String
    prediction_mapping = {0: 'Heart', 1: 'Oblong', 2: 'Oval', 3: 'Round', 4: 'Square'}

    # Load and preprocess the image for the model
    img = image.load_img(img_path, target_size=(150, 150))
    img_tensor = image.img_to_array(img)
    img_tensor = np.expand_dims(img_tensor, axis=0)
    img_tensor /= 255.
    imgs = np.vstack([img_tensor])

    # Load the model and predict
    loaded_model = models.load_model(model_path)
    prediction = loaded_model.predict(imgs)
    pred = list(prediction[0])
    index = pred.index(max(pred))
    confidence = round(max(pred)*100, 2)

    return prediction_mapping[index], confidence


@app.route('/', methods = ['GET', 'POST'])
def handle_request():
    model_path = "faceshapeone_vgg16_augment.h5"

    files_ids = list(flask.request.files)
    image_num = 1
    predicted_face_shape = "NO FACE"
    confidence = 1_000_000_000_0000

    for file_id in files_ids:
        imagefile = flask.request.files[file_id]
        filename = werkzeug.utils.secure_filename(imagefile.filename)
        timestr = time.strftime("%Y%m%d-%H%M%S")
        img_path = timestr + '-' + filename
        imagefile.save(img_path)

        image_num = image_num + 1

        predicted_face_shape, confidence = predict_face_shape(img_path, model_path)

        if image_num > 1:
            break

    return str(predicted_face_shape + "\nConfidence Level: " + str(confidence) + "%" )

app.run(host="0.0.0.0", port=5000, debug=True)


'''
if __name__ == '__main__':
    # Put location of the file path
    model_path = "faceshapeone_vgg16_augment.h5"

    filenames = ["round_" + str(i) + ".jpg" for i in range(1, 4)]
    print("Testing Round Face:")
    for filename in filenames:
        predicted_face_shape = predict_face_shape(filename, model_path)
        print("Predicted Face Shape: " + predicted_face_shape)

    filenames = ["heart_" + str(i) + ".jpg" for i in range(1, 4)]
    print("Testing Heart Face:")
    for filename in filenames:
        predicted_face_shape = predict_face_shape(filename, model_path)
        print("Predicted Face Shape: " + predicted_face_shape)

    filenames = ["oblong_" + str(i) + ".jpg" for i in range(1, 4)]
    print("Testing Oblong Face:")
    for filename in filenames:
        predicted_face_shape = predict_face_shape(filename, model_path)
        print("Predicted Face Shape: " + predicted_face_shape)

    filenames = ["square_" + str(i) + ".jpg" for i in range(1, 4)]
    print("Testing square Face:")
    for filename in filenames:
        predicted_face_shape = predict_face_shape(filename, model_path)
        print("Predicted Face Shape: " + predicted_face_shape)

    filenames = ["oval_" + str(i) + ".jpg" for i in range(1, 4)]
    print("Testing oval Face:")
    for filename in filenames:
        predicted_face_shape = predict_face_shape(filename, model_path)
        print("Predicted Face Shape: " + predicted_face_shape)
'''




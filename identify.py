from deepface import DeepFace

picture_to_find = "558.jpg"
database = "bildes"

print(DeepFace.find(img_path=picture_to_find, db_path=database, distance_metric="euclidean_l2"))
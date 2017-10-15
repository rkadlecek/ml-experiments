#!/usr/bin/env bash

INPUT_FOLDER="resources/experiment8/input/arff"
OUTPUT_FOLDER="resources/experiment8/output"

TRAINING_FILE_NAME="${1}"

TRAINING_FILE="${INPUT_FOLDER}/${TRAINING_FILE_NAME}.arff"
TESTING_FILE="${INPUT_FOLDER}/${2}.arff"

JAR_PATH="target/ml-experiments.jar"
JAVA_CLASS="sk.kadlecek.mle.Evaluation"

PARAMS="-t ${TRAINING_FILE} -s ${TESTING_FILE}"


#ALGORITHMS=( "J48" "DecisionStump" "PART" "DecisionTable" "RandomForest" "NaiveBayes" "SMO" "MultilayerPerceptron" )
ALGORITHMS=( "J48" "DecisionStump" "PART" "RandomForest" "NaiveBayes" "SMO" "MultilayerPerceptron" )
#ALGORITHMS=( "J48" )

for ALGORITHM in "${ALGORITHMS[@]}"
do

printf "\n\nRunning algorithm ${ALGORITHM} on training file ${TRAINING_FILE} and testing file ${TESTING_FILE}."
mkdir -p "resources/experiment8/output/${TRAINING_FILE_NAME}/"
java -cp "${JAR_PATH}" "${JAVA_CLASS}" -a "${ALGORITHM}" ${PARAMS} | tee "resources/experiment8/output/${TRAINING_FILE_NAME}/output_${ALGORITHM}.csv"

done

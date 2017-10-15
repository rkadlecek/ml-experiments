#!/usr/bin/env bash

FEATURESET_NO=$1
INPUT_FILE="resources/experiment7/input/gb_me_db_dataset_${FEATURESET_NO}.arff"

JAR_PATH="target/ml-experiments.jar"
JAVA_CLASS="sk.kadlecek.mle.CrossValidationEvaluation"

PARAMS="-f 10 -r 10 -d ${INPUT_FILE}"

#ALGORITHMS=( "J48" "DecisionStump" "PART" "DecisionTable" "RandomForest" "NaiveBayes" "SMO" "MultilayerPerceptron" )
ALGORITHMS=( "J48" "DecisionStump" "PART" "RandomForest" "NaiveBayes" "SMO" )
#ALGORITHMS=( "J48" )

for ALGORITHM in "${ALGORITHMS[@]}"
do

printf "\n\nRunning algorithm ${ALGORITHM} on input file ${INPUT_FILE}."
java -cp "${JAR_PATH}" "${JAVA_CLASS}" -a "${ALGORITHM}" ${PARAMS} | tee "resources/experiment7/output/${FEATURESET_NO}/output_${ALGORITHM}.csv"

done

ALGORITHM="MultilayerPerceptron"
printf "\n\nRunning algorithm ${ALGORITHM} on input file ${INPUT_FILE}."
java -cp "${JAR_PATH}" "${JAVA_CLASS}" -a "${ALGORITHM}" -f 10 -r 1 -d ${INPUT_FILE} | tee "resources/experiment7/output/${FEATURESET_NO}/output_${ALGORITHM}.csv"


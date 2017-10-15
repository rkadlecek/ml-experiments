# ml-experiments

A simple tool for evaluation of ML algorithms using Weka framework [1]. 

## Building package

A jar package can be built using following maven command:

```
$ mvn clean package
``` 
A jar package `ml-experiments.jar` will be created inside of the `target` directory.
## Running the application 

Built jar file is not runnable on its own, instead one 
of supported runnable classes (`SingleDataset`, `SeparateDatasets`), which should be run, needs to be provided.

The output of the application is written to `stdout`.

### Running with single dataset
When running with single dataset, the dataset will be automatically splitted to 10 pairs of (traning,testing) datasets, 
which then will be used for cross evaluation. 
The `SingleDataset` application takes 1 argument: path to the dataset to be used.  

```
$ java -cp target/ml-experiments.jar sk.kadlecek.mle.SingleDataset resources/experiment1/input/phones.arff 
```

### Running with separate training and testing datasets 
When running with separate datasets, the training dataset will be used 
to train the classifier algorithms, and then testing dataset will be used to 
evaluate its efficiency. 

The `SeparateDatasets` application takes 2 arguments: 
+ path to the training dataset
+ path to the testing dataset
 
```
$ java -cp target/ml-experiments.jar sk.kadlecek.mle.SeparateDatasets resources/experiment2/input/phones.arff resources/experiment2/input/tablets.arff
```

### Running Algorithm Configuration Evaluation

There is a runnable class, called *Evaluation*, which launches the chosen algorithm on given training and testing datasets.
The Evaluation application takes following command line arguments:

```
usage: Evaluation 
 -a,--algorithm <algorithm>                   algorithm to evaluate 
 -b,--best-config-only                        Use only best algorithm
                                              configuration for the same algorithm, do not
                                              evaluate multiple algorithm
                                              configurations
 -h,--help                                    print help
 -s,--testing-set <testing-set-file-path>     path to testing set file
 -t,--training-set <training-set-file-path>   path to training set file
 -v,--vectorize-strings                       vectorize strings (if input value is a string instead of
                                              set of features)
```


*Supported Algorithms*:

+ J48
+ DecisionStump
+ PART
+ DecisionTable
+ RandomForest
+ NaiveBayes
+ SMO
+ MultilayerPerceptron

Example:

```
java -cp target/ml-experiments.jar sk.kadlecek.mle.Evaluation -a NaiveBayes -t resources/experiment4/input/gb_phones.arff -s resources/experiment4/input/me_phones.arff 
```

```
java -cp target/ml-experiments.jar sk.kadlecek.mle.Evaluation -a NaiveBayes -t resources/experiment4/input/gb_phones.arff -s resources/experiment4/input/me_phones.arff 
```

Note: Evaluation of configurations of some algorithms, especially *RandomForest* and *MultilayerPerceptron* may take long time to complete.

#### Results

The results are printed to stdout and have the following tab separated form:

```
{UseSupervisedDiscretization=true}	72.99%	50.27%	27.01%	23.86%	421.00ms
{UseSupervisedDiscretization=false}	77.75%	25.50%	22.25%	20.70%	533.00ms
```

Columns description:
+ Algorithm configuration description - description of used configuration parameters.
+ Average Error Rate (%)
+ Average Precision (%)
+ Average Recall (%)
+ Average F-Measure (%)
+ Average time of the algorithm run (training + testing) (ms)

### Running Algorithm Configuration N-Fold CrossValidation Evaluation

This evaluation is similar to the previous method, but does not use 2 different datasets for training and testing. Instead,
it takes a single dataset as input, which is then split into N random folds, or (training, testing) dataset pairs.

The number of runs of the crossfold validation can be also configured using the `-r` option (defaults to 10 if not provided).
Usually is recommended to use 10-fold cross validation and run it 10 times. 

For each pair a new classifier of the same kind is created, trained and evaluated. The resulting evaluation is an average
of the metrics through all runs.

```
usage: CrossfoldEvaluation
 -a,--algorithm <algorithm>               algorithm to evaluate (J48,
                                          DecisionStump, DecisionTable,
                                          PART, RandomForest, NaiveBayes,
                                          SMO, MultilayerPerceptron)
-b,--best-config-only                     Use only best algorithm
                                          configuration for the same algorithm, do not
                                          evaluate multiple algorithm
                                          configurations                                         
 -d,--data-set <dataset-file-path>        path to data set file
 -f,--number-of-folds <number_of_folds>   Set number of folds for the
                                          crossfold evaluation. Defaults to 10.
 -h,--help                                print help
 -r,--number-of-runs <number_of_runs>     Set number of runs of the
                                           crossfold evaluation
```

For list of supported Algorithms see previous section. 

Example:

```
java -cp target/ml-experiments.jar sk.kadlecek.mle.CrossValidationEvaluation -a J48 -f 10 -r 10 -d resources/experiment4/gb_phones_dataset.arff
```

Note: Evaluation of configurations of some algorithms, especially *RandomForest* and *MultilayerPerceptron* may take long time to complete.

#### Results

Results of this evaluation are printed to stdout in the same form as described in previous section.

### Predicting Classes

This application will, instead of evaluations, generate the class label predictions for the given testing dataset.
Before the actual classification, the used classifier(-s) are trained using given training dataset.

Usage:
```
usage: Prediction
 -a,--algorithm <algorithm>                   algorithm to evaluate (J48,
                                              DecisionStump,
                                              DecisionTable, PART,
                                              RandomForest, NaiveBayes,
                                              SMO, MultilayerPerceptron)
 -b,--best-config-only                        Use only best algorithm
                                              configuration for the same
                                              algorithm, do not evaluate
                                              multiple algorithm
                                              configurations
 -h,--help                                    print help
 -o,--output-path <output-path>               Absolute Path (directory) on
                                              the filesystem, where the
                                              result file(s) will be
                                              stored.)
 -s,--testing-set <testing-set-file-path>     path to testing set file
 -t,--training-set <training-set-file-path>   path to training set file
```

Example:

```
java -cp target/ml-experiments.jar sk.kadlecek.mle.Prediction -a J48 -t resources/experiment9/input/arff/gb_db_training_dataset.arff -s resources/experiment9/input/arff/me_testing_dataset.arff -o resources/experiment9/output/ 
```

#### Output
The results are stored as separate files inside the given output path. Each file name is in form `algorithm_N.arff`, where `algorithm`
determines the used algorithm, and `N` determines the configuration of the algorithm. Also, a mapping file called `algorithm_mapping.txt`
is generated inside the output folder. This file contains the mapping before the `N-indices` and the corresponding algorithm properties. 
 
### Interactive Class Prediction

This application accepts a tranining set and classifier algorithm name. It trains given classifier using the training dataset,
and then provides an interactive shell, where the user can query the classifier for any string input.

To exit the application, type `exit` or press `ctrl+d`.


Usage:
```
usage: InteractivePrediction
 -a,--algorithm <algorithm>                   algorithm to evaluate (J48,
                                              DecisionStump,
                                              DecisionTable, PART,
                                              RandomForest, NaiveBayes,
                                              SMO, MultilayerPerceptron)
 -h,--help                                    print help
 -t,--training-set <training-set-file-path>   path to training set file
```

Example:

```
java -cp target/ml-experiments.jar sk.kadlecek.mle.InteractivePrediction -a J48 -t resources/experiment9/input/arff/gb_db_training_dataset.arff 
```

```
Classificating using RandomForest. Type 'exit' to exit.
Enter input to classify: 3gb
Result[3gb]: 1,3,2,1,0,0,false,false,false,false,true,RAM_CAPACITY
```
 
 
## Resources

[1] Weka framework: http://www.cs.waikato.ac.nz/~ml/weka/

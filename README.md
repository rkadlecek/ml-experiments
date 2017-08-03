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
$ java -cp target/ml-experiments.jar sk.kadlecek.mle.SingleDataset resources/experiment1/phones_dataset.arff 
```

### Running with separate training and testing datasets 
When running with separate datasets, the training dataset will be used 
to train the classifier algorithms, and then testing dataset will be used to 
evaluate its efficiency. 

The `SeparateDatasets` application takes 2 arguments: 
+ path to the training dataset
+ path to the testing dataset
 
```
$ java -cp target/ml-experiments.jar sk.kadlecek.mle.SeparateDatasets resources/experiment2/phones_dataset.arff resources/experiment2/tablets_dataset.arff
```

### Running Algorithm Configuration Evaluation

There is a runnable class, called *Evaluation*, which launches the chosen algorithm on given training and testing datasets.
The Evaluation application takes following command line arguments:

```
usage: Evaluation 
 -a,--algorithm <algorithm>                   algorithm to evaluate 
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
java -cp target/ml-experiments.jar sk.kadlecek.mle.Evaluation -a NaiveBayes -t resources/experiment4/gb_phones_dataset.arff -s resources/experiment4/me_phones_dataset.arff 
```

```
java -cp target/ml-experiments.jar sk.kadlecek.mle.Evaluation -a NaiveBayes -v -t resources/experiment5/gb_phones.arff -s resources/experiment5/me_phones.arff 
```

Note: Evaluation of configurations of some algorithms, especially *RandomForest* and *MultilayerPerceptron* may take long time to complete.

## Resources

[1] Weka framework: http://www.cs.waikato.ac.nz/~ml/weka/

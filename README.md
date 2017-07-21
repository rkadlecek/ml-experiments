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
## Resources

Weka framework: http://www.cs.waikato.ac.nz/~ml/weka/
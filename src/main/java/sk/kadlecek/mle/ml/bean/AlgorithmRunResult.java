package sk.kadlecek.mle.ml.bean;

import weka.classifiers.Evaluation;

public class AlgorithmRunResult {

    private String classifierName;
    private Evaluation evaluation;
    private Long time;

    public AlgorithmRunResult(String classifierName, Evaluation evaluation, Long time) {
        this.classifierName = classifierName;
        this.evaluation = evaluation;
        this.time = time;
    }

    public String getClassifierName() {
        return classifierName;
    }

    public void setClassifierName(String classifierName) {
        this.classifierName = classifierName;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

}

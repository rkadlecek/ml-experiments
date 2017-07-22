package sk.kadlecek.mle.ml.bean;

public class AlgorithmStats {

    private String classifierName;
    private Double avgWeightedAccuracy;
    private Double avgWeightedPrecision;
    private Double avgWeightedRrecall;
    private Double avgWeightedFMeasure;
    private Double avgTime;

    public Double getAvgWeightedAccuracy() {
        return avgWeightedAccuracy;
    }

    public void setAvgWeightedAccuracy(Double avgWeightedAccuracy) {
        this.avgWeightedAccuracy = avgWeightedAccuracy;
    }

    public Double getAvgWeightedPrecision() {
        return avgWeightedPrecision;
    }

    public void setAvgWeightedPrecision(Double avgWeightedPrecision) {
        this.avgWeightedPrecision = avgWeightedPrecision;
    }

    public Double getAvgWeightedRecall() {
        return avgWeightedRrecall;
    }

    public void setAvgWeightedRrecall(Double avgWeightedRrecall) {
        this.avgWeightedRrecall = avgWeightedRrecall;
    }

    public Double getAvgWeightedFMeasure() {
        return avgWeightedFMeasure;
    }

    public void setAvgWeightedFMeasure(Double avgWeightedFMeasure) {
        this.avgWeightedFMeasure = avgWeightedFMeasure;
    }

    public Double getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(Double avgTime) {
        this.avgTime = avgTime;
    }

    public String getClassifierName() {
        return classifierName;
    }

    public void setClassifierName(String classifierName) {
        this.classifierName = classifierName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(classifierName + ":");
//        sb.append(" Average Accuracy:" + String.format("%.2f%%", getAvgWeightedAccuracy() * 100));
        sb.append("\tAverage Precision:" + String.format("%.2f%%", getAvgWeightedPrecision() * 100));
        sb.append("\tAverage Recall:" + String.format("%.2f%%", getAvgWeightedRecall() * 100));
        sb.append("\tAverage FMeasure:" + String.format("%.2f%%", getAvgWeightedFMeasure() * 100));
        sb.append("\tAverage Time:" + String.format("%.2fms", getAvgTime()));
        //sb.append("\n---------------------------------\n");
        return sb.toString();
    }

    public String toStringNoLabels() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%.2f%%", getAvgWeightedPrecision() * 100) + "\t");
        sb.append(String.format("%.2f%%", getAvgWeightedRecall() * 100) + "\t");
        sb.append(String.format("%.2f%%", getAvgWeightedFMeasure() * 100) + "\t");
        sb.append(String.format("%.2fms", getAvgTime()));
        return sb.toString();
    }
}

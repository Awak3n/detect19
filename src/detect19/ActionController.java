package detect19;

import ADReNA_API.Data.DataSet;
import ADReNA_API.Data.DataSetObject;
import ADReNA_API.NeuralNetwork.Backpropagation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ActionController implements ActionListener, ItemListener {

    MainClient main;

    ActionController(MainClient main) {
        this.main = main;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Integer febre;
        Integer espirros;
        Integer narizEntupido;
        Integer dorDeCabeca;
        Integer faltaDeAr;
        if (e.getSource() == main.jm.jButton1) {
            if (main.jm.jComboBox1.getSelectedItem().toString().equals("Sim")) {
                febre = 1;
            } else {
                febre = 0;
            }
            if (main.jm.jComboBox2.getSelectedItem().toString().equals("Sim")) {
                espirros = 1;
            } else {
                espirros = 0;
            }
            if (main.jm.jComboBox3.getSelectedItem().toString().equals("Sim")) {
                narizEntupido = 1;
            } else {
                narizEntupido = 0;
            }
            if (main.jm.jComboBox4.getSelectedItem().toString().equals("Sim")) {
                dorDeCabeca = 1;
            } else {
                dorDeCabeca = 0;
            }
            if (main.jm.jComboBox5.getSelectedItem().toString().equals("Sim")) {
                faltaDeAr = 1;
            } else {
                faltaDeAr = 0;
            }
            showMessage(getDoenca(febre, espirros, narizEntupido, dorDeCabeca, faltaDeAr));
            System.out.println("" + febre + espirros + narizEntupido + dorDeCabeca + faltaDeAr);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
    }

    //atualiza o status
    public final void showMessage(final String text) {
        main.jm.jTextField1.setText(text);
    }

    private String getResposta(double n1, double n2){
        if(n1 >= 0.5 && n2 >= 0.5){
            return "Desconhecido";
        } else if(n1 >= 0.5 && n2 < 0.5){
            return "Gripe";
        } else if(n1 < 0.5 && n2 >= 0.5){
            return "Resfriado";
        } else if(n1 < 0.5 && n2 < 0.5){
            return "Covid-19";
        }    
        return "ERROR";
    }
    
    private String getDoenca(Integer febre, Integer espirros, Integer narizEntupido, Integer dorDeCabeca, Integer faltaDeAr) {
        double n1 = 0.0; 
        double n2 = 0.0;
        try {

            Backpropagation minhaRNA = new Backpropagation(5, 2, new int[]{20});
            minhaRNA.SetLearningRate(0.15);
            minhaRNA.SetErrorRate(0.001);
            minhaRNA.SetMaxIterationNumber(100000);

            DataSet trainingSet = new DataSet(5, 2);

            trainingSet.Add(new DataSetObject(new double[]{0, 0, 0, 0, 0}, new double[]{1, 1}));
            trainingSet.Add(new DataSetObject(new double[]{0, 1, 1, 0, 0}, new double[]{0, 1}));
            trainingSet.Add(new DataSetObject(new double[]{1, 0, 1, 1, 0}, new double[]{1, 0}));
            trainingSet.Add(new DataSetObject(new double[]{1, 0, 0, 1, 0}, new double[]{1, 0}));
            trainingSet.Add(new DataSetObject(new double[]{1, 0, 0, 1, 1}, new double[]{0, 0}));
            trainingSet.Add(new DataSetObject(new double[]{1, 0, 0, 0, 1}, new double[]{0, 0}));

            minhaRNA.Learn(trainingSet);
            
            n1 = minhaRNA.Recognize(new double[] {febre, espirros, narizEntupido, dorDeCabeca, faltaDeAr})[0];
            n2 = minhaRNA.Recognize(new double[] {febre, espirros, narizEntupido, dorDeCabeca, faltaDeAr})[1];
            
        } catch (Exception exception) {
            System.out.println("Exception: " + exception.toString());
        }
        
        return getResposta(n1, n2);
    }
    
}

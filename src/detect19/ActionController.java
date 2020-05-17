package detect19;

import ADReNA_API.Data.DataSet;
import ADReNA_API.Data.DataSetObject;
import ADReNA_API.NeuralNetwork.Backpropagation;
import ADReNA_API.NeuralNetwork.Kohonen;

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

    private String getDoenca(Integer febre, Integer espirros, Integer narizEntupido, Integer dorDeCabeca, Integer faltaDeAr) {

        try {

            Backpropagation minhaRNA = new Backpropagation(5, 1, new int[]{});
            minhaRNA.SetLearningRate(0.1);
            minhaRNA.SetErrorRate(0.005);
            minhaRNA.SetMaxIterationNumber(10000);

            DataSet trainingSet = new DataSet(5, 1);

            trainingSet.Add(new DataSetObject(new double[]{0, 0, 0, 0, 0}, new double[]{0}));
            
            trainingSet.Add(new DataSetObject(new double[]{0, 0, 0, 0, 1}, new double[]{0}));
            trainingSet.Add(new DataSetObject(new double[]{0, 0, 0, 1, 0}, new double[]{0}));
            trainingSet.Add(new DataSetObject(new double[]{0, 0, 1, 0, 0}, new double[]{0}));
            trainingSet.Add(new DataSetObject(new double[]{0, 1, 0, 0, 0}, new double[]{0}));
            trainingSet.Add(new DataSetObject(new double[]{1, 0, 0, 0, 0}, new double[]{0}));
            
            trainingSet.Add(new DataSetObject(new double[]{1, 0, 0, 0, 1}, new double[]{0}));
            trainingSet.Add(new DataSetObject(new double[]{1, 0, 0, 1, 0}, new double[]{0}));
            trainingSet.Add(new DataSetObject(new double[]{1, 0, 1, 0, 0}, new double[]{0}));
            trainingSet.Add(new DataSetObject(new double[]{1, 1, 0, 0, 0}, new double[]{0}));
            
            trainingSet.Add(new DataSetObject(new double[]{1, 1, 0, 0, 1}, new double[]{0}));
            trainingSet.Add(new DataSetObject(new double[]{1, 1, 0, 1, 0}, new double[]{0}));
            trainingSet.Add(new DataSetObject(new double[]{1, 1, 1, 0, 0}, new double[]{0}));
            
            trainingSet.Add(new DataSetObject(new double[]{1, 1, 1, 0, 1}, new double[]{0}));
            trainingSet.Add(new DataSetObject(new double[]{1, 1, 1, 1, 0}, new double[]{0}));
            
            trainingSet.Add(new DataSetObject(new double[]{1, 1, 1, 1, 1}, new double[]{1}));

            minhaRNA.Learn(trainingSet);
            
            System.out.println("Resposta: " + minhaRNA.Recognize(new double[] {febre, espirros, narizEntupido, dorDeCabeca, faltaDeAr})[0]);

        } catch (Exception exception) {
            System.out.println("Exception: " + exception.toString());
        }

        return "TODO";
    }
    //controla os dados que chegam do servidor, marcando no campo do cliente
}

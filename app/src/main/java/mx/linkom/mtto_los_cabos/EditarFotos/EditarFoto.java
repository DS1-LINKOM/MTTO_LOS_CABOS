package mx.linkom.mtto_los_cabos.EditarFotos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Build;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;

public class EditarFoto {


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Bitmap fechaHoraFoto(Bitmap foto){

//        Matrix matrix = new Matrix();
//        matrix.postRotate(90);
//
//        Bitmap rotatedBitmap = Bitmap.createBitmap(foto, 0, 0, foto.getWidth(), foto.getHeight(), matrix, true);
//
//        foto = rotatedBitmap;

        Log.e("foto", "Método fechahora" );
        Mat selected_image = new Mat(foto.getHeight(), foto.getWidth(), CvType.CV_8UC4);
        Utils.bitmapToMat(foto, selected_image);

        // Define the text to be drawn
        LocalDateTime hoy = LocalDateTime.now();

        int year = hoy.getYear();
        int month = hoy.getMonthValue();
        int day = hoy.getDayOfMonth();
        int hour = hoy.getHour();
        int minute = hoy.getMinute();
        int second = hoy.getSecond();

        String fecha = "";

        //Poner el cero cuando el mes o dia es menor a 10
        if (day < 10 || month < 10) {
            if (month < 10 && day >= 10) {
                fecha = year + "/0" + month + "/" + day;
            } else if (month >= 10 && day < 10) {
                fecha = year + "/" + month + "/0" + day;
            } else if (month < 10 && day < 10) {
                fecha = year + "/0" + month + "/0" + day;
            }
        } else {
            fecha = year + "-" + month + "-" + day;
        }

        String hora = "";

        if (hour < 10 || minute < 10) {
            if (hour < 10 && minute >= 10) {
                hora = "0" + hour + ":" + minute;
            } else if (hour >= 10 && minute < 10) {
                hora = hour + ":0" + minute;
            } else if (hour < 10 && minute < 10) {
                hora = "0" + hour + ":0" + minute;
            }
        } else {
            hora = hour + ":" + minute;
        }

        String segundos = "00";

        if (second < 10) {
            segundos = "0" + second;
        } else {
            segundos = "" + second;
        }

        //String text = fecha + " " + hora;


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
        String formattedDate = dateFormat.format(calendar.getTime());

        Log.e("FECHA", formattedDate);
        Log.e("FECHA", "Tamaño: " + formattedDate.length() + " menos 5: " + (formattedDate.length() - 6));

        String fechahora = "";

        for (int i = 0; i < formattedDate.length(); i++) {
            char c = formattedDate.charAt(i);

            if (i > (formattedDate.length() - 6)){

                fechahora += (Character.isWhitespace(c) || !Character.isLetterOrDigit(c) && c != '.')? "" : c;

//                if (Character.isWhitespace(c) || !Character.isLetterOrDigit(c) && c != '.'){
//                }else {
//                    fechahora += formattedDate.charAt(i);
//                }
            }else {
                fechahora += formattedDate.charAt(i);
            }

        }

        String text = fechahora;

        double fontScale=0.0;
        int thickness=0;
        int posicionInicioLetrasX=0, posicionInicioLetrasY1=0, posicionInicioLetrasY2=0, posicionInicioLetrasY3=0;
        if (selected_image.height() <= 135){
            fontScale = 0.2;
            thickness = 1;
            posicionInicioLetrasX = 10;
            posicionInicioLetrasY1 = 15;
            posicionInicioLetrasY2 = 25;
            posicionInicioLetrasY3 = 35;
        }else if (selected_image.height() <= 235){
            fontScale = 0.4;
            thickness = 1;
            posicionInicioLetrasX = 20;
            posicionInicioLetrasY1 = 25;
            posicionInicioLetrasY2 = 40;
            posicionInicioLetrasY3 = 55;
        }else if (selected_image.height() <= 535){
            fontScale = 0.7;
            thickness = 2;
            posicionInicioLetrasX = 20;
            posicionInicioLetrasY1 = 50;
            posicionInicioLetrasY2 = 85;
            posicionInicioLetrasY3 = 120;
        }else if (selected_image.height() <= 835){
            fontScale = 1;
            thickness = 2;
            posicionInicioLetrasX = 20;
            posicionInicioLetrasY1 = 50;
            posicionInicioLetrasY2 = 95;
            posicionInicioLetrasY3 = 140;
        }else if (selected_image.height() <= 1235){
            fontScale = 1.3;
            thickness = 2;
            posicionInicioLetrasX = 20;
            posicionInicioLetrasY1 = 50;
            posicionInicioLetrasY2 = 110;
            posicionInicioLetrasY3 = 170;
        }else if (selected_image.height() <= 1535){
            fontScale = 2.2;
            thickness = 3;
            posicionInicioLetrasX = 40;
            posicionInicioLetrasY1 = 80;
            posicionInicioLetrasY2 = 170;
            posicionInicioLetrasY3 = 250;
        }else if (selected_image.height() <= 1935){
            fontScale = 3.4;
            thickness = 6;
            posicionInicioLetrasX = 40;
            posicionInicioLetrasY1 = 120;
            posicionInicioLetrasY2 = 240;
            posicionInicioLetrasY3 = 370;
        }else if (selected_image.height() <= 2435){
            fontScale = 4.0;
            thickness = 8;
            posicionInicioLetrasX = 40;
            posicionInicioLetrasY1 = 140;
            posicionInicioLetrasY2 = 300;
            posicionInicioLetrasY3 = 450;
        }else if (selected_image.height() <= 2835){
            fontScale = 5.0;
            thickness = 10;
            posicionInicioLetrasX = 40;
            posicionInicioLetrasY1 = 150;
            posicionInicioLetrasY2 = 340;
            posicionInicioLetrasY3 = 510;
        }else if (selected_image.height() <= 3535){
            fontScale = 6.0;
            thickness = 12;
            posicionInicioLetrasX = 40;
            posicionInicioLetrasY1 = 170;
            posicionInicioLetrasY2 = 380;
            posicionInicioLetrasY3 = 590;
        }else if (selected_image.height() <= 4435){
            fontScale = 7.0;
            thickness = 14;
            posicionInicioLetrasX = 40;
            posicionInicioLetrasY1 = 190;
            posicionInicioLetrasY2 = 430;
            posicionInicioLetrasY3 = 670;
        }else if (selected_image.height() <= 5335){
            fontScale = 8.0;
            thickness = 16;
            posicionInicioLetrasX = 80;
            posicionInicioLetrasY1 = 250;
            posicionInicioLetrasY2 = 520;
            posicionInicioLetrasY3 = 800;
        }else if (selected_image.height() <= 6335){
            fontScale = 9.0;
            thickness = 18;
            posicionInicioLetrasX = 80;
            posicionInicioLetrasY1 = 270;
            posicionInicioLetrasY2 = 590;
            posicionInicioLetrasY3 = 920;
        }else if (selected_image.height() <= 7035){
            fontScale = 10.0;
            thickness = 20;
            posicionInicioLetrasX = 80;
            posicionInicioLetrasY1 = 290;
            posicionInicioLetrasY2 = 640;
            posicionInicioLetrasY3 = 1020;
        }else if (selected_image.height() <= 8035){
            fontScale = 11.0;
            thickness = 24;
            posicionInicioLetrasX = 140;
            posicionInicioLetrasY1 = 320;
            posicionInicioLetrasY2 = 740;
            posicionInicioLetrasY3 = 1170;
        }


        // Define the font face, scale, color, thickness, and line type
        int fontFace = Core.FONT_HERSHEY_SIMPLEX;
        //double fontScale = 5.0;
        Scalar color = new Scalar(255, 0, 0); // White color
        //int thickness = 25;
        int lineType = Imgproc.LINE_AA;

        // Define the text's baseline anchor point
        Point org = new Point(posicionInicioLetrasX, posicionInicioLetrasY1);
        Point org2 = new Point(posicionInicioLetrasX, posicionInicioLetrasY2);
        Point org3 = new Point(posicionInicioLetrasX, posicionInicioLetrasY3);

        // Draw the text on the image

        Imgproc.putText(selected_image, "LINK MTTO", org, fontFace, fontScale, color, thickness, lineType, false);

        Imgproc.putText(selected_image, fecha, org2, fontFace, fontScale, color, thickness, lineType, false);

        Imgproc.putText(selected_image, hora, org3, fontFace, fontScale, color, thickness, lineType, false);




        Bitmap bitmap =Bitmap.createBitmap(selected_image.cols(),selected_image.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(selected_image,bitmap);

        // escribir texto en el marco
        // cadena de clase nombre del objeto // punto de partida // color del texto // tamaño del texto
        //Imgproc.putText(foto,labelList.get((int) class_value),new Point(left,top),3,1,new Scalar(255, 0, 0, 255),2);

        return bitmap;
    }
}
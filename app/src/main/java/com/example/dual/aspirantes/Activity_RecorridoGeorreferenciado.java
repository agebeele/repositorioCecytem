package com.example.dual.aspirantes;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.dual.R;

public class Activity_RecorridoGeorreferenciado extends AppCompatActivity implements SensorEventListener {
    //actualizar
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;
    // Variables para almacenar datos de los sensores
    private float[] accelerometerValues = new float[3];
    private float[] magnetometerValues = new float[3];
    private static final int PERMISSION_REQUEST_CODE = 100;
    private LocationMonitor locationMonitor;
    private static final String[] placeNames = {"Edificio A", "Edificio B","Edificio C","Edificio D","Edificio E","Edifico F","Cafeteria","Explanada","Cancha Voli","Cancha Basquet","Cancha Futbol","Maquinas Ejercicio","Entrada Principal"};
    private static final double[][] buildingVerticesLat = {
            {19.621549, 19.621466, 19.621438, 19.621357}, // Edificio A, latitudes de las esquinas
            {19.621355, 19.621294, 19.621246, 19.621188},  // Edificio B, latitudes de las esquinas
            {19.621404, 19.621301, 19.621295, 19.621195}, // Edificio C, latitudes de las esquinas
            {19.621244, 19.621111, 19.621136, 19.621002}, // Edificio D, latitudes de las esquinas
            {19.621970, 19.621866, 19.621859, 19.621756}, // Edificio E, latitudes de las esquinas
            {19.621790, 19.621684, 19.621677, 19.621573}, // Edificio F, latitudes de las esquinas
            {19.621748, 19.621705, 19.621636, 19.621592},// Cafeteria, latitudes de las esquinas
            {19.621642, 19.621448, 19.621343, 19.621525},// Explanada, latitudes de las esquinas
            {19.621189, 19.621152, 19.621011, 19.620973},// Cancha Voli, latitudes de las esquinas
            {19.621144, 19.621070, 19.621043, 19.620970},// Cancha Basquet, latitudes de las esquinas
            {19.620563, 19.620459, 19.620431, 19.620326},// Cancha Futbol, latitudes de las esquinas
            {19.620909, 19.620900, 19.621021, 19.621011},// Maquinas Ejercicio, latitudes de las esquinas
            {19.621660, 19.621546, 19.621508, 19.621659}// Entrada Principal, latitudes de las esquinas
    };
    private static final double[][] buildingVerticesLng = {
            {-99.100473, -99.100221, -99.100512, -99.100261}, // Edificio A, longitudes de las esquinas
            {-99.100515, -99.100325, -99.100553, -99.100362},  // Edificio B, longitudes de las esquinas
            {-99.101010, -99.100701, -99.101051, -99.100741},  // Edificio C, longitudes de las esquinas
            {-99.101134, -99.100737, -99.101175, -99.100778}, // Edificio D, longitudes de las esquinas
            {-99.100858, -99.100549, -99.100898, -99.100591}, // Edificio E, longitudes de las esquinas
            {-99.100925, -99.100618, -99.100966, -99.100659}, // Edificio F, longitudes de las esquinas
            {-99.100434, -99.100303, -99.100476, -99.100346},// Cafeteria, latitudes de las esquinas
            {-99.100981, -99.101046, -99.100733, -99.100617},// Explanada, latitudes de las esquinas
            {-99.100507, -99.100398, -99.100573, -99.100465},// Cancha Voli, latitudes de las esquinas
            {-99.100340, -99.100140, -99.100381, -99.100181},// Cancha Basquet, latitudes de las esquinas
            {-99.100614, -99.100339, -99.100671, -99.100395},// Cancha Futbol, latitudes de las esquinas
            {-99.100702, -99.100680, -99.100653, -99.100631},// Maquinas Ejercicio, latitudes de las esquinas
            {-99.101246, -99.101289, -99.101092, -99.101016}// Entrada Principal, latitudes de las esquinas
    };
    private TextView placeTitle, placeDescription;
    private ImageView placeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorrido_georreferenciado);
        placeTitle = findViewById(R.id.placeTitle);
        placeDescription = findViewById(R.id.placeDescription);
        placeImage = findViewById(R.id.placeImage);
        locationMonitor = new LocationMonitor(this);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        } else {
            locationMonitor.startMonitoring();
        }

        // Inicializar el SensorManager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Obtener referencias a los sensores
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        // Registrar el oyente del sensor
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationMonitor.startMonitoring();
            } else {
                Toast.makeText(this, "La aplicación requiere permiso para acceder a la ubicación.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationMonitor.stopMonitoring();

        // Desregistrar el oyente del sensor cuando la actividad se destruye
        sensorManager.unregisterListener(this);
    }

    public void updatePlaceInfo(int nearestPlaceIndex) {
        String title;
        String description;
        int imageResourceId;

        switch (nearestPlaceIndex) {
            case 0: //Edificio A
                title = placeNames[nearestPlaceIndex];
                description = "En este Edifico puedes encontrar lo siguiente: \n" +
                        "\n" +
                        "PLANTA BAJA\n" +
                        "Biblioteca.\n" +
                        "Enfermeria.\n" +
                        "PLANTA ALTA" +
                        "Direccion Academica.\n" +
                        "Subdirecion Academica.\n" +
                        "Sala de maestros.\n";
                imageResourceId = R.drawable.edificioab;
                break;
            case 1: //Edificio B
                title = placeNames[nearestPlaceIndex];
                description = "En este edificio puedes encontrar lo siguiente: \n" +
                        "\n" +
                        "PLANTA BAJA\n" +
                        "Auditorio.\n" +
                        "PLANTA ALTA\n" +
                        "Area de Vinculacion Adademica (Atiente en ventanilla).\n" +
                        "Subdireccion Administrativa .\n" +
                        "Control Escolar (Atiente en ventanilla).\n"+
                        "Club de Lectura.\n";
                imageResourceId = R.drawable.edificioab;
                break;
            case 2:// Edificio C
                title = placeNames[nearestPlaceIndex];
                description = "En este Edificio se puede encontrar lo siguiente:\n" +
                        "PLANTA BAJA.\n" +
                        "Laboratorio de computo\n" +
                        "PLANTA ALTA\n" +
                        "Salones de clase del (agregar salones)";
                imageResourceId = R.drawable.edificioc;
                break;
            case 3: //Edificio D
                title = placeNames[nearestPlaceIndex];
                description = "En este Edificio se puede encontrar lo siguiente:\n" +
                        "PLANTA BAJA.\n" +
                        "Laboratorio Multifuncional y bodegas\n" +
                        "PLANTA ALTA\n" +
                        "Salones de clase del (agregar salones)";
                imageResourceId = R.drawable.edificiod;
                break;
            case 4: //Edificio E
                title = placeNames[nearestPlaceIndex];
                description = "En este Edificio se puede encontrar lo siguiente:\n" +
                        "\n" +
                        "PLANTA BAJA.\n" +
                        "Papeleria (Antes de subir o bajar a los salones.\n" +
                        "Salones de clase del (agregar salones).\n" +
                        "PLANTA ALTA\n" +
                        "Salones de clase del (agregar salones).\n";
                imageResourceId = R.drawable.edificiofe;
                break;
            case 5: //Edificio F
                title = placeNames[nearestPlaceIndex];
                description = "En este Edificio se puede encontar lo siguiente: \n" +
                        "\n" +
                        "PLANTA BAJA\n" +
                        "Laboratorio de ingles.\n" +
                        "Laboratorio de Mantenimiento.\n" +
                        "Orientacion Escolar.\n" +
                        "PLANTA ALTA\n" +
                        "Salones de clase del (agregar salones)";
                imageResourceId = R.drawable.edificiofe;
                break;
            case 6: //Cafeteria
                title = placeNames[nearestPlaceIndex];
                description = "Area de los alumnos y maestros que ofrece:\n" +
                        "\n" +
                        "Comida hecha al momento.\n" +
                        "Botanas y dulces.\n" +
                        "Mesas y sillas fuera y dentro de la cafeteria.";
                imageResourceId = R.drawable.cafeteria;
                break;
            case 7: //Explanada
                title = placeNames[nearestPlaceIndex];
                description = "Estas en nuestra Explanada cerca de dos edificios donde se imparten clases: \n " +
                        "\n" +
                        "A tu mano izquierda tienes: \n" +
                        "EDIFICIO F Y EDIFICIO E.\n" +
                        "A tu mano derecha tienes: \n" +
                        "EDIFICIO C Y EDIFICIO D.\n" +
                        "Hacia el frente tienes: \n" +
                        "EDIFICIO B Y EDIFICIO A.";
                imageResourceId = R.drawable.explanada;
                break;
            case 8: //Cancha Voli
                title = placeNames[nearestPlaceIndex];
                description = "Area deportiva para los alumnos donde pueden practicar lo siguiente:\n" +
                        "\n" +
                        "Volleyball.\n" +
                        "Basketball.\n" +
                        "Dos Canchas se encuentra techadas y las otras dos no\n";
                imageResourceId = R.drawable.canchas;
                break;
            case 9: //Cancha Basquet
                title = placeNames[nearestPlaceIndex];
                description = "Area deportiva para los alumnos donde pueden practicar lo siguiente:\n" +
                        "\n" +
                        "Volleyball.\n" +
                        "Basketball.\n" +
                        "Dos Canchas se encuentra techadas y las otras dos no\n";
                imageResourceId = R.drawable.canchas;
                break;
            case 10: //Cancha Futbol
                title = placeNames[nearestPlaceIndex];
                description = "Area para que los alumnos puedan jugar de manera segura:\n" +
                        "\n" +
                        "Se puede pedir el acceso al area de Subdireccion Administrativa.\n";
                imageResourceId = R.drawable.futboll;
                break;
            case 11: //Maquinas Ejercicio
                title = placeNames[nearestPlaceIndex];
                description = "Area para los alumnos y puedan ejercitarse cuenta con: \n" +
                        "\n" +
                        "Aparato para correr.\n" +
                        "Aparato para levantar tu propio peso.\n" +
                        "Aparato para hacer abdominales y otros más.\n" +
                        "Frente ahi se encuentran unos tubos para hacer barra";
                imageResourceId = R.drawable.gym;
                break;
            case 12: //Entrada Principal
                title = placeNames[nearestPlaceIndex];
                description = "Bienvenido a nuestras instalaciones donde tenemos las siguientes carreras:\n" +
                        "\n" +
                        "Soporte y Mantenimiento en equipos de computo.\n" +
                        "Desarrollo Organizacional.\n" +
                        "Programacion.\n" +
                        "¡Nuestras instalaciones te estan esperando!.";
                imageResourceId = R.drawable.cecytem;
                break;
            default:
                title = "Lugar Desconocido";
                description = "No te encuentras cerca de ningún edificio conocido.";
                imageResourceId = R.drawable.logo;
                break;
        }

        placeTitle.setText(title);
        placeDescription.setText(description);
        placeImage.setImageResource(imageResourceId);
    }

    public void onLocationUpdate(Location location) {
        double userLat = location.getLatitude();
        double userLng = location.getLongitude();
        int nearestPlaceIndex = -1;

        for (int i = 0; i < placeNames.length; i++) {
            double[] buildingVerticesLatitudes = buildingVerticesLat[i];
            double[] buildingVerticesLongitudes = buildingVerticesLng[i];

            if (isInsideBuilding(userLat, userLng, buildingVerticesLatitudes, buildingVerticesLongitudes)) {
                nearestPlaceIndex = i;
                break;
            }
        }

        if (nearestPlaceIndex != -1) {
            updatePlaceInfo(nearestPlaceIndex);
            placeImage.setVisibility(View.VISIBLE);
        } else {
            placeTitle.setText("No estás dentro de ningún edificio.");
            placeDescription.setText("Sigue caminando para encontrar un edificio.");
            placeImage.setImageResource(R.drawable.logo);
            placeImage.setVisibility(View.VISIBLE);
        }
    }

    private boolean isInsideBuilding(double userLat, double userLng, double[] buildingVerticesLatitudes, double[] buildingVerticesLongitudes) {
        double minLat = Double.MAX_VALUE;
        double maxLat = Double.MIN_VALUE;
        double minLng = Double.MAX_VALUE;
        double maxLng = Double.MIN_VALUE;

        // Calcular el centroide del rectángulo del edificio
        double centroidLat = 0.0;
        double centroidLng = 0.0;
        for (int i = 0; i < buildingVerticesLatitudes.length; i++) {
            if (buildingVerticesLatitudes[i] < minLat) minLat = buildingVerticesLatitudes[i];
            if (buildingVerticesLatitudes[i] > maxLat) maxLat = buildingVerticesLatitudes[i];
            if (buildingVerticesLongitudes[i] < minLng) minLng = buildingVerticesLongitudes[i];
            if (buildingVerticesLongitudes[i] > maxLng) maxLng = buildingVerticesLongitudes[i];

            centroidLat += buildingVerticesLatitudes[i];
            centroidLng += buildingVerticesLongitudes[i];
        }
        centroidLat /= buildingVerticesLatitudes.length;
        centroidLng /= buildingVerticesLongitudes.length;

        // Definir un margen de tolerancia (puedes ajustar este valor según sea necesario)
        double tolerance = 0.000130;

        // Verificar si la ubicación dada está dentro del rectángulo del edificio con margen de tolerancia
        return (userLat >= (centroidLat - tolerance) && userLat <= (centroidLat + tolerance) &&
                userLng >= (centroidLng - tolerance) && userLng <= (centroidLng + tolerance));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == accelerometer) {
            System.arraycopy(event.values, 0, accelerometerValues, 0, 3);
        } else if (event.sensor == magnetometer) {
            System.arraycopy(event.values, 0, magnetometerValues, 0, 3);
        }

        // Aquí puedes utilizar los datos del acelerómetro y la brújula para mejorar la determinación de la ubicación del usuario.
        // Puedes calcular la orientación o utilizar otros algoritmos según tus necesidades.
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No es necesario implementar esto aquí, pero es obligatorio por la interfaz SensorEventListener.
    }
}

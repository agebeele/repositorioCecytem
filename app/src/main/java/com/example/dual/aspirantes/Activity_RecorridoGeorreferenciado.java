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
            {19.621442506334194, 19.621455138623904, 19.621361659656547, 19.62143745341804}, // Edificio A, latitudes de las esquinas
            {19.62134650089995, 19.62128081293821, 19.621197439717335, 19.621258074791356},  // Edificio B, latitudes de las esquinas
            {19.621290918780225, 19.621194913255426, 19.621290918780225, 19.621389450706566}, // Edificio C, latitudes de las esquinas
            {19.62109750966048, 19.621002915642297, 19.62123238143732, 19.621135495919397}, // Edificio D, latitudes de las esquinas
            {19.621666687962552, 19.621571492869688, 19.621675612499626, 19.621773782374635}, // Edificio E, latitudes de las esquinas
            {19.621845178609703, 19.62174998362256, 19.621849640873343, 19.621953760323073}, // Edificio F, latitudes de las esquinas
            {19.62165032630997, 19.621769320108882, 19.62172023517751, 19.62160421618922},// Cafeteria, latitudes de las esquinas
            {19.62144059952187, 19.621605703609607, 19.621491171963143, 19.621336479736755},// Explanada, latitudes de las esquinas
            {19.62118327478407, 19.621050893694537, 19.62093041193402, 19.621059818265795},// Cancha Voli, latitudes de las esquinas
            {19.620985446820775, 19.620909587914152, 19.620659699497953, 19.620748945405484},// Cancha Basquet, latitudes de las esquinas
            {19.620583840432214, 19.620421710163562, 19.62047228292545, 19.620322052027348},// Cancha Futbol, latitudes de las esquinas
            {19.621035742456634, 19.62099409444694, 19.620876587504252, 19.620916748114585},// Maquinas Ejercicio, latitudes de las esquinas
            {19.621595014695966, 19.621562291374552, 19.62149684471176, 19.621516181228543}// Entrada Principal, latitudes de las esquinas
    };
    private static final double[][] buildingVerticesLng = {
            {-99.10051515875033, -99.1002388912194, -99.1002737599369, -99.10050711212321}, // Edificio A, longitudes de las esquinas
            {-99.10051515875033, -99.10033813295381, -99.10036495504418, -99.1005553918859},  // Edificio B, longitudes de las esquinas
            {-99.10071364221915, -99.10075655756374, -99.10105696497604, -99.10102746067659},  // Edificio C, longitudes de las esquinas
            {-99.10077672642707, -99.10080786384027, -99.10114350621622, -99.10118501038097}, // Edificio D, longitudes de las esquinas
            {-99.10064410379672, -99.10067726535205, -99.10098519408007, -99.10094413691633}, // Edificio E, longitudes de las esquinas
            {-99.10057620156438, -99.10060936311972, -99.10090939623933, -99.10087939292737}, // Edificio F, longitudes de las esquinas
            {-99.10051777406214, -99.10046566304665, -99.10030459263506, -99.10034091243375},// Cafeteria, latitudes de las esquinas
            {-99.10101835563432, -99.10096150725374, -99.10065357852572, -99.10069463568941},// Explanada, latitudes de las esquinas
            {-99.10049566636324, -99.10010720242941, -99.10015141783653, -99.10055093562211},// Cancha Voli, latitudes de las esquinas
            {-99.10045145095539, -99.1001561552008, -99.10025090250176, -99.1005383026479},// Cancha Basquet, latitudes de las esquinas
            {-99.10061883785262, -99.10069147745, -99.10032038385468, -99.10038986520871},// Cancha Futbol, latitudes de las esquinas
            {-99.1007366655636, -99.10063560177596, -99.10068613366977, -99.100809305161},// Maquinas Ejercicio, latitudes de las esquinas
            {-99.10124672186454, -99.1011535536853, -99.10118355699726, -99.10127356693313}// Entrada Principal, latitudes de las esquinas
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
        int verticesCount = buildingVerticesLatitudes.length;
        boolean inside = false;

        for (int i = 0, j = verticesCount - 1; i < verticesCount; j = i++) {
            if ((buildingVerticesLongitudes[i] > userLng) != (buildingVerticesLongitudes[j] > userLng) &&
                    (userLat < (buildingVerticesLatitudes[j] - buildingVerticesLatitudes[i]) * (userLng - buildingVerticesLongitudes[i]) / (buildingVerticesLongitudes[j] - buildingVerticesLongitudes[i]) + buildingVerticesLatitudes[i])) {
                inside = !inside;
            }
        }
        return inside;
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
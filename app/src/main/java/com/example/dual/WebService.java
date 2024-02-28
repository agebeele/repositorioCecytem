package com.example.dual;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class WebService {
    public String login(String matricula, String curp) {
        String aux = "";
        try {
            URL url = new URL("http://192.168.137.143:80/conexion_cecytem/login_usuarios.php");

            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setDoOutput(true);
            OutputStreamWriter data1 = new OutputStreamWriter(conexion.getOutputStream());

            String data = "matricula=" + URLEncoder.encode(matricula, "UTF-8")
                    +  "&curp=" + URLEncoder.encode(curp, "UTF-8");

            data1.write(data);
            data1.flush();
            data1.close();

            if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                String linea = reader.readLine();
                while (linea != null) {
                    aux = aux + linea; // Concatenar datos línea por línea
                    linea = reader.readLine(); // Leer siguiente línea
                }
                reader.close(); // Cerrar buffer de lectura
                if (aux.equals("2002")) {
                    aux = "ERROR DE CONEXION AL SERVIDOR DE DATOS";
                } else if (aux.equals("001")) {
                    aux = "Correo sin validar";
                } else if (aux.equals("000")) {
                    aux = "No se pudo mostrar la contraseña";
                } else if (aux.equals("010")) {
                    aux = "El Usuario o Contraseña no existe";
                }
            } else {
                aux = "ERROR al procesar servicio: " + conexion.getResponseCode();
            }
            conexion.disconnect();
        } catch (Exception ex) {
            aux = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return aux;
    }
    public String registarUsuario (String matricula, String curp, String nombre, String paterno, String materno, String carreraActual) {
        String response = "";
        try {
            URL url = new URL("http://192.168.137.143:80/conexion_cecytem/registro_usuarios.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Construye los datos a enviar en el cuerpo de la solicitud
            String data = "matricula=" + URLEncoder.encode(matricula, "UTF-8")
                    + "&curp=" + URLEncoder.encode(curp, "UTF-8")
                    + "&nombre=" + URLEncoder.encode(nombre, "UTF-8")
                    + "&apellido_paterno=" + URLEncoder.encode(paterno, "UTF-8")
                    + "&apellido_materno=" + URLEncoder.encode(materno, "UTF-8")
                    + "&carreraActual=" + URLEncoder.encode(carreraActual, "UTF-8");

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(data);
            writer.flush();
            writer.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response += line;
                }
                if (response.equals("002")) {
                    response = "Usuario creado";
                } else if (response.equals("000")) {
                    response = "No se pudo crear el registro";
                } else {
                    response += line;
                }
                reader.close();
            } else {
                response = "ERROR al procesar el servicio: " + connection.getResponseCode();
            }
            connection.disconnect();
        } catch (Exception ex) {
            response = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return response;
    }
    public String datosUsuario(String matricula) {
        String aux = "";
        try {
            URL url = new URL("http://192.168.137.143:80/conexion_cecytem/mostrar_datos.php");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(conexion.getOutputStream());
            String data = "matricula=" + URLEncoder.encode(matricula, "UTF-8"); // Pasar el correo electrónico del usuario
            writer.write(data);
            writer.flush();
            writer.close();
            if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                String linea = reader.readLine();
                if (!linea.equals("010")) { // Verificar si no es un error de "No se encontraron resultados"
                    aux = linea; // Recuperar el JSON con los datos del usuario
                } else {
                    aux = "No se encontraron resultados"; // Manejar el caso de usuario no encontrado
                }
                reader.close(); // Cerrar buffer de lectura
            } else {
                aux = "ERROR al procesar servicio: " + conexion.getResponseCode();
            }
            conexion.disconnect();
        } catch (Exception ex) {
            aux = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return aux;
    }
    public String muroPublicaciones() {
        String aux = "";
        try {
            URL url = new URL("http://192.168.137.143:80/conexion_cecytem/publicaciones_buscar.php");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setDoOutput(true);
            OutputStreamWriter datSal = new OutputStreamWriter(conexion.getOutputStream());
            String data = "id=" + URLEncoder.encode("1", "UTF-8");;
            datSal.write(data);
            datSal.flush();
            datSal.close();
            if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                String linea = reader.readLine();
                while (linea != null) {
                    aux = aux + linea; // Concatenar datos línea por línea
                    linea = reader.readLine(); // Leer siguiente línea
                }
                reader.close(); // Cerrar buffer de lectura
                if (aux.equals("2002")) {
                    aux = "ERROR DE CONEXION AL SERVIDOR DE DATOS";
                } else if (aux.equals("001")) {
                    aux = "Sin ID para validar";
                } else if (aux.equals("000")) {
                    aux = "No se pudo mostrar la ID";
                } else if (aux.equals("010")) {
                    aux = "No se encontraron resultados";
                }
            } else {
                aux = "ERROR al procesar servicio: " + conexion.getResponseCode();
            }
            conexion.disconnect();
        } catch (Exception ex) {
            aux = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return aux;
    }
    public String login_admin(String matricula, String curp) {
        String aux = "";
        try {
            URL url = new URL("http://192.168.137.143:80/conexion_cecytem/login_admin.php");

            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setDoOutput(true);
            OutputStreamWriter data1 = new OutputStreamWriter(conexion.getOutputStream());

            String data = "matricula=" + URLEncoder.encode(matricula, "UTF-8")
                    +  "&curp=" + URLEncoder.encode(curp, "UTF-8");

            data1.write(data);
            data1.flush();
            data1.close();

            if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                String linea = reader.readLine();
                while (linea != null) {
                    aux = aux + linea; // Concatenar datos línea por línea
                    linea = reader.readLine(); // Leer siguiente línea
                }
                reader.close(); // Cerrar buffer de lectura
                if (aux.equals("2002")) {
                    aux = "ERROR DE CONEXION AL SERVIDOR DE DATOS";
                } else if (aux.equals("001")) {
                    aux = "Correo sin validar";
                } else if (aux.equals("000")) {
                    aux = "No se pudo mostrar la contraseña";
                } else if (aux.equals("010")) {
                    aux = "El Usuario o Contraseña no existe";
                }
            } else {
                aux = "ERROR al procesar servicio: " + conexion.getResponseCode();
            }
            conexion.disconnect();
        } catch (Exception ex) {
            aux = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return aux;
    }
    public String login_adminVIN(String matricula, String curp) {
        String aux = "";
        try {
            URL url = new URL("http://192.168.137.143:80/conexion_cecytem/login_adminVin.php");

            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setDoOutput(true);
            OutputStreamWriter data1 = new OutputStreamWriter(conexion.getOutputStream());

            String data = "matricula=" + URLEncoder.encode(matricula, "UTF-8")
                    +  "&curp=" + URLEncoder.encode(curp, "UTF-8");

            data1.write(data);
            data1.flush();
            data1.close();

            if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                String linea = reader.readLine();
                while (linea != null) {
                    aux = aux + linea; // Concatenar datos línea por línea
                    linea = reader.readLine(); // Leer siguiente línea
                }
                reader.close(); // Cerrar buffer de lectura
                if (aux.equals("2002")) {
                    aux = "ERROR DE CONEXION AL SERVIDOR DE DATOS";
                } else if (aux.equals("001")) {
                    aux = "Correo sin validar";
                } else if (aux.equals("000")) {
                    aux = "No se pudo mostrar la contraseña";
                } else if (aux.equals("010")) {
                    aux = "El Usuario o Contraseña no existe";
                }
            } else {
                aux = "ERROR al procesar servicio: " + conexion.getResponseCode();
            }
            conexion.disconnect();
        } catch (Exception ex) {
            aux = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return aux;
    }
    public String agregarEvento(String titulo, String descripcion, String fecha) {
        String aux = "";
        try {
            URL url = new URL("http://192.168.137.143:80/conexion_cecytem/eventos.php");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setDoOutput(true);

            OutputStreamWriter datSal = new OutputStreamWriter(conexion.getOutputStream());
            String data = "titulo=" + URLEncoder.encode(titulo, "UTF-8") +
                    "&descripcion=" + URLEncoder.encode(descripcion, "UTF-8") +
                    "&fecha=" + URLEncoder.encode(fecha, "UTF-8");
            datSal.write(data);
            datSal.flush();
            datSal.close();

            if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Manejar la respuesta del servidor si es necesario
            } else {
                aux = "ERROR al procesar servicio: " + conexion.getResponseCode();
            }

            conexion.disconnect();
        } catch (Exception ex) {
            aux = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return aux;
    }
    public String obtenerEventos(String fecha) {
        String aux = "";
        try {
                URL url = new URL("http://192.168.137.143:80/conexion_cecytem/mostrar_eventos.php");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setDoOutput(true);

            OutputStreamWriter datSal = new OutputStreamWriter(conexion.getOutputStream());
            String data = "fecha=" + URLEncoder.encode(fecha, "UTF-8");
            datSal.write(data);
            datSal.flush();
            datSal.close();

            if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Leer la respuesta del servidor
                BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();
                aux = response.toString();
            } else {
                aux = "ERROR al procesar servicio: " + conexion.getResponseCode();
            }

            conexion.disconnect();
        } catch (Exception ex) {
            aux = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return aux;
    }
    public String datosDomicilio(String matricula) {
        String aux = "";
        try {
            URL url = new URL("http://192.168.137.143:80/conexion_cecytem/mostrar_domicilio.php");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(conexion.getOutputStream());
            String data = "matricula=" + URLEncoder.encode(matricula, "UTF-8"); // Pasar el correo electrónico del usuario
            writer.write(data);
            writer.flush();
            writer.close();
            if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                String linea = reader.readLine();
                if (!linea.equals("010")) { // Verificar si no es un error de "No se encontraron resultados"
                    aux = linea; // Recuperar el JSON con los datos del usuario
                } else {
                    aux = "No se encontraron resultados"; // Manejar el caso de usuario no encontrado
                }
                reader.close(); // Cerrar buffer de lectura
            } else {
                aux = "ERROR al procesar servicio: " + conexion.getResponseCode();
            }
            conexion.disconnect();
        } catch (Exception ex) {
            aux = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return aux;
    }
    public String datosAdminCE(String matricula) {
        String aux = "";
        try {
            URL url = new URL("http://192.168.137.143:80/conexion_cecytem/datos_adminCE.php");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(conexion.getOutputStream());
            String data = "matricula=" + URLEncoder.encode(matricula, "UTF-8"); // Pasar el correo electrónico del usuario
            writer.write(data);
            writer.flush();
            writer.close();
            if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                String linea = reader.readLine();
                if (!linea.equals("010")) { // Verificar si no es un error de "No se encontraron resultados"
                    aux = linea; // Recuperar el JSON con los datos del usuario
                } else {
                    aux = "No se encontraron resultados"; // Manejar el caso de usuario no encontrado
                }
                reader.close(); // Cerrar buffer de lectura
            } else {
                aux = "ERROR al procesar servicio: " + conexion.getResponseCode();
            }
            conexion.disconnect();
        } catch (Exception ex) {
            aux = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return aux;
    }
    public String datosAdminVIN(String matricula) {
        String aux = "";
        try {
            URL url = new URL("http://192.168.137.143:80/conexion_cecytem/datos_adrminVIN.php");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(conexion.getOutputStream());
            String data = "matricula=" + URLEncoder.encode(matricula, "UTF-8"); // Pasar el correo electrónico del usuario
            writer.write(data);
            writer.flush();
            writer.close();
            if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                String linea = reader.readLine();
                if (!linea.equals("010")) { // Verificar si no es un error de "No se encontraron resultados"
                    aux = linea; // Recuperar el JSON con los datos del usuario
                } else {
                    aux = "No se encontraron resultados"; // Manejar el caso de usuario no encontrado
                }
                reader.close(); // Cerrar buffer de lectura
            } else {
                aux = "ERROR al procesar servicio: " + conexion.getResponseCode();
            }
            conexion.disconnect();
        } catch (Exception ex) {
            aux = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return aux;
    }
    public String solicitudesCredencial() {
        String aux = "";
        try {
            URL url = new URL("http://192.168.137.143:80/conexion_cecytem/solicitudes_credencial.php");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setDoOutput(true);

            // No enviamos la matrícula en este caso

            if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                String linea = reader.readLine();
                while (linea != null) {
                    aux = aux + linea; // Concatenar datos línea por línea
                    linea = reader.readLine(); // Leer siguiente línea
                }
                reader.close(); // Cerrar buffer de lectura
                // Resto del código para manejar la respuesta del servidor...
            } else {
                aux = "ERROR al procesar servicio: " + conexion.getResponseCode();
            }
            conexion.disconnect();
        } catch (Exception ex) {
            aux = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return aux;
    }
    public String solicitudesConstancia() {
        String aux = "";
        try {
            URL url = new URL("http://192.168.137.143:80/conexion_cecytem/solicitudes_constancia.php");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setDoOutput(true);

            // No enviamos la matrícula en este caso

            if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                String linea = reader.readLine();
                while (linea != null) {
                    aux = aux + linea; // Concatenar datos línea por línea
                    linea = reader.readLine(); // Leer siguiente línea
                }
                reader.close(); // Cerrar buffer de lectura
                // Resto del código para manejar la respuesta del servidor...
            } else {
                aux = "ERROR al procesar servicio: " + conexion.getResponseCode();
            }
            conexion.disconnect();
        } catch (Exception ex) {
            aux = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return aux;
    }
    public String solicitudesHistorial() {
        String aux = "";
        try {
            URL url = new URL("http://192.168.137.143:80/conexion_cecytem/solicitudes_historial.php");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setDoOutput(true);

            // No enviamos la matrícula en este caso

            if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                String linea = reader.readLine();
                while (linea != null) {
                    aux = aux + linea; // Concatenar datos línea por línea
                    linea = reader.readLine(); // Leer siguiente línea
                }
                reader.close(); // Cerrar buffer de lectura
                // Resto del código para manejar la respuesta del servidor...
            } else {
                aux = "ERROR al procesar servicio: " + conexion.getResponseCode();
            }
            conexion.disconnect();
        } catch (Exception ex) {
            aux = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return aux;
    }
    public String solicitudesBecas() {
        String aux = "";
        try {
            URL url = new URL("http://192.168.137.143:80/conexion_cecytem/solicitudes_becas.php");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setDoOutput(true);

            // No enviamos la matrícula en este caso

            if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                String linea = reader.readLine();
                while (linea != null) {
                    aux = aux + linea; // Concatenar datos línea por línea
                    linea = reader.readLine(); // Leer siguiente línea
                }
                reader.close(); // Cerrar buffer de lectura
                // Resto del código para manejar la respuesta del servidor...
            } else {
                aux = "ERROR al procesar servicio: " + conexion.getResponseCode();
            }
            conexion.disconnect();
        } catch (Exception ex) {
            aux = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return aux;
    }
    public String solicitudesServicio() {
        String aux = "";
        try {
            URL url = new URL("http://192.168.137.143:80/conexion_cecytem/solicitudes_servicio.php");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setDoOutput(true);

            // No enviamos la matrícula en este caso

            if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                String linea = reader.readLine();
                while (linea != null) {
                    aux = aux + linea; // Concatenar datos línea por línea
                    linea = reader.readLine(); // Leer siguiente línea
                }
                reader.close(); // Cerrar buffer de lectura
                // Resto del código para manejar la respuesta del servidor...
            } else {
                aux = "ERROR al procesar servicio: " + conexion.getResponseCode();
            }
            conexion.disconnect();
        } catch (Exception ex) {
            aux = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return aux;
    }
    public String solicitudesEventos() {
        String aux = "";
        try {
            URL url = new URL("http://192.168.137.143:80/conexion_cecytem/solicitudes_eventos.php");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setDoOutput(true);

            // No enviamos la matrícula en este caso

            if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                String linea = reader.readLine();
                while (linea != null) {
                    aux = aux + linea; // Concatenar datos línea por línea
                    linea = reader.readLine(); // Leer siguiente línea
                }
                reader.close(); // Cerrar buffer de lectura
                // Resto del código para manejar la respuesta del servidor...
            } else {
                aux = "ERROR al procesar servicio: " + conexion.getResponseCode();
            }
            conexion.disconnect();
        } catch (Exception ex) {
            aux = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return aux;
    }
    public String solicitudesCambioGrupo() {
        String aux = "";
        try {
            URL url = new URL("http://192.168.137.143:80/conexion_cecytem/solicitudes_cambioGrupo.php");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setDoOutput(true);

            // No enviamos la matrícula en este caso

            if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                String linea = reader.readLine();
                while (linea != null) {
                    aux = aux + linea; // Concatenar datos línea por línea
                    linea = reader.readLine(); // Leer siguiente línea
                }
                reader.close(); // Cerrar buffer de lectura
                // Resto del código para manejar la respuesta del servidor...
            } else {
                aux = "ERROR al procesar servicio: " + conexion.getResponseCode();
            }
            conexion.disconnect();
        } catch (Exception ex) {
            aux = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return aux;
    }
    public String solicitudesCambioTurno() {
        String aux = "";
        try {
            URL url = new URL("http://192.168.137.143:80/conexion_cecytem/solicitudes_cambioTurno.php");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setDoOutput(true);

            // No enviamos la matrícula en este caso

            if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                String linea = reader.readLine();
                while (linea != null) {
                    aux = aux + linea; // Concatenar datos línea por línea
                    linea = reader.readLine(); // Leer siguiente línea
                }
                reader.close(); // Cerrar buffer de lectura
                // Resto del código para manejar la respuesta del servidor...
            } else {
                aux = "ERROR al procesar servicio: " + conexion.getResponseCode();
            }
            conexion.disconnect();
        } catch (Exception ex) {
            aux = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return aux;
    }
    public String solicitudesCambioCarrera() {
        String aux = "";
        try {
            URL url = new URL("http://192.168.137.143:80/conexion_cecytem/solicitudes_cambioCarrera.php");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setDoOutput(true);

            // No enviamos la matrícula en este caso

            if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                String linea = reader.readLine();
                while (linea != null) {
                    aux = aux + linea; // Concatenar datos línea por línea
                    linea = reader.readLine(); // Leer siguiente línea
                }
                reader.close(); // Cerrar buffer de lectura
                // Resto del código para manejar la respuesta del servidor...
            } else {
                aux = "ERROR al procesar servicio: " + conexion.getResponseCode();
            }
            conexion.disconnect();
        } catch (Exception ex) {
            aux = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return aux;
    }
    public String solicitudesCambioPlantel() {
        String aux = "";
        try {
            URL url = new URL("http://192.168.137.143:80/conexion_cecytem/solicitudes_cambioPlantel.php");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setDoOutput(true);

            // No enviamos la matrícula en este caso

            if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                String linea = reader.readLine();
                while (linea != null) {
                    aux = aux + linea; // Concatenar datos línea por línea
                    linea = reader.readLine(); // Leer siguiente línea
                }
                reader.close(); // Cerrar buffer de lectura
                // Resto del código para manejar la respuesta del servidor...
            } else {
                aux = "ERROR al procesar servicio: " + conexion.getResponseCode();
            }
            conexion.disconnect();
        } catch (Exception ex) {
            aux = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return aux;
    }
    public String crearPublicacion (String titulo, String descripcion, String fecha, String hora) {
        String response = "";
        try {
            URL url = new URL("http://192.168.137.143:80/conexion_cecytem/crearPublicacion.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Construye los datos a enviar en el cuerpo de la solicitud
            String data = "titulo=" + URLEncoder.encode(titulo, "UTF-8")
                    + "&descripcion=" + URLEncoder.encode(descripcion, "UTF-8")
                    + "&fecha=" + URLEncoder.encode(fecha, "UTF-8")
                    + "&hora=" + URLEncoder.encode(hora, "UTF-8");

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(data);
            writer.flush();
            writer.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response += line;
                }
                if (response.equals("002")) {
                    response = "Publicacion creado";
                } else if (response.equals("000")) {
                    response = "No se pudo crear la publicacion";
                } else {
                    response += line;
                }
                reader.close();
            } else {
                response = "ERROR al procesar el servicio: " + connection.getResponseCode();
            }
            connection.disconnect();
        } catch (Exception ex) {
            response = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return response;
    }
    public String solicitarCredencial (String matricula, String grupo, String nombre, String paterno, String materno, String fecha, String hora) {
        String response = "";
        try {
            URL url = new URL("http://192.168.137.143:80/conexion_cecytem/solicitar_credencial.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Construye los datos a enviar en el cuerpo de la solicitud
            String data = "matricula=" + URLEncoder.encode(matricula, "UTF-8")
                    + "&grupo=" + URLEncoder.encode(grupo, "UTF-8")
                    + "&fecha=" + URLEncoder.encode(fecha, "UTF-8")
                    + "&hora=" + URLEncoder.encode(hora, "UTF-8")
                    + "&nombre=" + URLEncoder.encode(nombre, "UTF-8")
                    + "&apellido_paterno=" + URLEncoder.encode(paterno, "UTF-8")
                    + "&apellido_materno=" + URLEncoder.encode(materno, "UTF-8");

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(data);
            writer.flush();
            writer.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response += line;
                }
                if (response.equals("002")) {
                    response = "Solicitud enviada";
                } else if (response.equals("000")) {
                    response = "No se pudo enviar la solicitud";
                } else {
                    response += line;
                }
                reader.close();
            } else {
                response = "ERROR al procesar el servicio: " + connection.getResponseCode();
            }
            connection.disconnect();
        } catch (Exception ex) {
            response = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return response;
    }
    public String solicitarConstancia(String matricula, String grupo, String nombre, String paterno, String materno, String fecha, String hora, String observaciones) {
        String response = "";
        try {
            URL url = new URL("http://192.168.137.143:80/conexion_cecytem/solicitar_constancia.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            String data = "matricula=" + URLEncoder.encode(matricula, "UTF-8")
                    + "&grupo=" + URLEncoder.encode(grupo, "UTF-8")
                    + "&nombre=" + URLEncoder.encode(nombre, "UTF-8")
                    + "&apellido_paterno=" + URLEncoder.encode(paterno, "UTF-8")
                    + "&apellido_materno=" + URLEncoder.encode(materno, "UTF-8")
                    + "&fecha=" + URLEncoder.encode(fecha, "UTF-8")
                    + "&hora=" + URLEncoder.encode(hora, "UTF-8")
                    + "&observaciones=" + URLEncoder.encode(observaciones, "UTF-8");


            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(data);
            writer.flush();
            writer.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response += line;
                }
                if (response.equals("002")) {
                    response = "Solicitud enviada";
                } else if (response.equals("000")) {
                    response = "No se pudo enviar la solicitud";
                } else {
                    response += line;
                }
                reader.close();
            } else {
                response = "ERROR al procesar el servicio: " + connection.getResponseCode();
            }
            connection.disconnect();
        } catch (Exception ex) {
            response = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return response;
    }
    public String solicitarHistorial (String matricula, String grupo, String nombre, String paterno, String materno, String fecha, String hora, String observaciones) {
        String response = "";
        try {
            URL url = new URL("http://192.168.137.143:80/conexion_cecytem/solicitar_historial.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Construye los datos a enviar en el cuerpo de la solicitud
            String data = "matricula=" + URLEncoder.encode(matricula, "UTF-8")
                    + "&grupo=" + URLEncoder.encode(grupo, "UTF-8")
                    + "&fecha=" + URLEncoder.encode(fecha, "UTF-8")
                    + "&hora=" + URLEncoder.encode(hora, "UTF-8")
                    + "&nombre=" + URLEncoder.encode(nombre, "UTF-8")
                    + "&apellido_paterno=" + URLEncoder.encode(paterno, "UTF-8")
                    + "&observaciones=" + URLEncoder.encode(observaciones, "UTF-8")
                    + "&apellido_materno=" + URLEncoder.encode(materno, "UTF-8");

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(data);
            writer.flush();
            writer.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response += line;
                }
                if (response.equals("002")) {
                    response = "Solicitud enviada";
                } else if (response.equals("000")) {
                    response = "No se pudo enviar la solicitud";
                } else {
                    response += line;
                }
                reader.close();
            } else {
                response = "ERROR al procesar el servicio: " + connection.getResponseCode();
            }
            connection.disconnect();
        } catch (Exception ex) {
            response = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return response;
    }
    public String solicitarCambioTurno(String matricula, String grupo, String nombre, String paterno, String materno, String fecha, String hora, String motivos, String turnoActual, String turnoCambio, String promedio) {
        String response = "";
        try {
            URL url = new URL("http://192.168.137.143:80/conexion_cecytem/solicitar_cambioTurno.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Construye los datos a enviar en el cuerpo de la solicitud
            String data = URLEncoder.encode("matricula", "UTF-8") + "=" + URLEncoder.encode(matricula, "UTF-8")
                    + "&" + URLEncoder.encode("grupo", "UTF-8") + "=" + URLEncoder.encode(grupo, "UTF-8")
                    + "&" + URLEncoder.encode("fecha", "UTF-8") + "=" + URLEncoder.encode(fecha, "UTF-8")
                    + "&" + URLEncoder.encode("hora", "UTF-8") + "=" + URLEncoder.encode(hora, "UTF-8")
                    + "&" + URLEncoder.encode("nombre", "UTF-8") + "=" + URLEncoder.encode(nombre, "UTF-8")
                    + "&" + URLEncoder.encode("apellido_paterno", "UTF-8") + "=" + URLEncoder.encode(paterno, "UTF-8")
                    + "&" + URLEncoder.encode("motivos", "UTF-8") + "=" + URLEncoder.encode(motivos, "UTF-8")
                    + "&" + URLEncoder.encode("turnoActual", "UTF-8") + "=" + URLEncoder.encode(turnoActual, "UTF-8")
                    + "&" + URLEncoder.encode("turnoCambio", "UTF-8") + "=" + URLEncoder.encode(turnoCambio, "UTF-8")
                    + "&" + URLEncoder.encode("promedio", "UTF-8") + "=" + URLEncoder.encode(promedio, "UTF-8")
                    + "&" + URLEncoder.encode("apellido_materno", "UTF-8") + "=" + URLEncoder.encode(materno, "UTF-8");

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(data);
            writer.flush();
            writer.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response += line;
                }
                if (response.equals("002")) {
                    response = "Solicitud enviada";
                } else if (response.equals("000")) {
                    response = "No se pudo enviar la solicitud";
                } else {
                    response += line;
                }
                reader.close();
            } else {
                response = "ERROR al procesar el servicio: " + connection.getResponseCode();
            }
            connection.disconnect();
        } catch (Exception ex) {
            response = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return response;
    }
    public String solicitarCambioGrupo (String matricula, String grupo, String nombre, String paterno, String materno, String fecha, String hora, String motivos, String grupoActual,String grupoCambio, String promedio) {
        String response = "";
        try {
            URL url = new URL("http://192.168.137.143:80/conexion_cecytem/solicitar_cambioGrupo.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Construye los datos a enviar en el cuerpo de la solicitud
            String data = "matricula=" + URLEncoder.encode(matricula, "UTF-8")
                    + "&grupo=" + URLEncoder.encode(grupo, "UTF-8")
                    + "&fecha=" + URLEncoder.encode(fecha, "UTF-8")
                    + "&hora=" + URLEncoder.encode(hora, "UTF-8")
                    + "&nombre=" + URLEncoder.encode(nombre, "UTF-8")
                    + "&apellido_paterno=" + URLEncoder.encode(paterno, "UTF-8")
                    + "&motivos=" + URLEncoder.encode(motivos, "UTF-8")
                    + "&grupoActual=" + URLEncoder.encode(grupoActual, "UTF-8")
                    + "&grupoCambio=" + URLEncoder.encode(grupoCambio, "UTF-8")
                    + "&promedio=" + URLEncoder.encode(promedio, "UTF-8")
                    + "&apellido_materno=" + URLEncoder.encode(materno, "UTF-8");

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(data);
            writer.flush();
            writer.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response += line;
                }
                if (response.equals("002")) {
                    response = "Solicitud enviada";
                } else if (response.equals("000")) {
                    response = "No se pudo enviar la solicitud";
                } else {
                    response += line;
                }
                reader.close();
            } else {
                response = "ERROR al procesar el servicio: " + connection.getResponseCode();
            }
            connection.disconnect();
        } catch (Exception ex) {
            response = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return response;
    }
    public String solicitarCambioCarrera (String matricula, String grupo, String nombre, String paterno, String materno, String fecha, String hora, String motivos, String carreraActual,String carreraCambio, String promedio) {
        String response = "";
        try {
            URL url = new URL("http://192.168.137.143:80/conexion_cecytem/solicitar_cambioCarrera.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Construye los datos a enviar en el cuerpo de la solicitud
            String data = "matricula=" + URLEncoder.encode(matricula, "UTF-8")
                    + "&grupo=" + URLEncoder.encode(grupo, "UTF-8")
                    + "&fecha=" + URLEncoder.encode(fecha, "UTF-8")
                    + "&hora=" + URLEncoder.encode(hora, "UTF-8")
                    + "&nombre=" + URLEncoder.encode(nombre, "UTF-8")
                    + "&apellido_paterno=" + URLEncoder.encode(paterno, "UTF-8")
                    + "&motivos=" + URLEncoder.encode(motivos, "UTF-8")
                    + "&carreraActual=" + URLEncoder.encode(carreraActual, "UTF-8")
                    + "&carreraCambio=" + URLEncoder.encode(carreraCambio, "UTF-8")
                    + "&promedio=" + URLEncoder.encode(promedio, "UTF-8")
                    + "&apellido_materno=" + URLEncoder.encode(materno, "UTF-8");

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(data);
            writer.flush();
            writer.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response += line;
                }
                if (response.equals("002")) {
                    response = "Solicitud enviada";
                } else if (response.equals("000")) {
                    response = "No se pudo enviar la solicitud";
                } else {
                    response += line;
                }
                reader.close();
            } else {
                response = "ERROR al procesar el servicio: " + connection.getResponseCode();
            }
            connection.disconnect();
        } catch (Exception ex) {
            response = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return response;
    }
    public String solicitarCambioPlantel (String matricula, String grupo, String nombre, String paterno, String materno, String fecha, String hora, String motivos, String plantelActual,String plantelCambio, String promedio) {
        String response = "";
        try {
            URL url = new URL("http://192.168.137.143:80/conexion_cecytem/solicitar_cambioPlantel.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Construye los datos a enviar en el cuerpo de la solicitud
            String data = "matricula=" + URLEncoder.encode(matricula, "UTF-8")
                    + "&grupo=" + URLEncoder.encode(grupo, "UTF-8")
                    + "&fecha=" + URLEncoder.encode(fecha, "UTF-8")
                    + "&hora=" + URLEncoder.encode(hora, "UTF-8")
                    + "&nombre=" + URLEncoder.encode(nombre, "UTF-8")
                    + "&apellido_paterno=" + URLEncoder.encode(paterno, "UTF-8")
                    + "&motivos=" + URLEncoder.encode(motivos, "UTF-8")
                    + "&plantelActual=" + URLEncoder.encode(plantelActual, "UTF-8")
                    + "&plantelCambio=" + URLEncoder.encode(plantelCambio, "UTF-8")
                    + "&promedio=" + URLEncoder.encode(promedio, "UTF-8")
                    + "&apellido_materno=" + URLEncoder.encode(materno, "UTF-8");

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(data);
            writer.flush();
            writer.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response += line;
                }
                if (response.equals("002")) {
                    response = "Solicitud enviada";
                } else if (response.equals("000")) {
                    response = "No se pudo enviar la solicitud";
                } else {
                    response += line;
                }
                reader.close();
            } else {
                response = "ERROR al procesar el servicio: " + connection.getResponseCode();
            }
            connection.disconnect();
        } catch (Exception ex) {
            response = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return response;
    }
    public String solicitarBeca(String curp, String nombre, String paterno,String materno, String fecha, String hora, String fechaNacimiento, String sexo, String estadoNacimiento, String situacionAcademica, String semestre, String correoPersonal, String correoInstitucional,String telefonoCasa, String telefonoAlumno, String telefonoPadre, String domicilio, String noExterior, String noInterior, String nombreColonia, String municipio, String estadoDomicilio, String codigoPostal, String escuelaProcedencia, String promedio) {
        String response = "";
        try {
            URL url = new URL("http://192.168.137.143:80/conexion_cecytem/solicitar_beca.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Construye los datos a enviar en el cuerpo de la solicitud
            String data = "curp=" + URLEncoder.encode(curp, "UTF-8")
                    + "&fecha=" + URLEncoder.encode(fecha, "UTF-8")
                    + "&hora=" + URLEncoder.encode(hora, "UTF-8")
                    + "&nombre=" + URLEncoder.encode(nombre, "UTF-8")
                    + "&apellido_paterno=" + URLEncoder.encode(paterno, "UTF-8")
                    + "&apellido_materno=" + URLEncoder.encode(materno, "UTF-8")
                    + "&fecha_nacimiento=" + URLEncoder.encode(fechaNacimiento, "UTF-8")
                    + "&sexo=" + URLEncoder.encode(sexo, "UTF-8")
                    + "&estado_nacimiento=" + URLEncoder.encode(estadoNacimiento, "UTF-8")
                    + "&situacion_academica=" + URLEncoder.encode(situacionAcademica, "UTF-8")
                    + "&semestre=" + URLEncoder.encode(semestre, "UTF-8")
                    + "&correo_personal=" + URLEncoder.encode(correoPersonal, "UTF-8")
                    + "&correo_institucional=" + URLEncoder.encode(correoInstitucional, "UTF-8")
                    + "&telefono_casa=" + URLEncoder.encode(telefonoCasa, "UTF-8")
                    + "&telefono_alumno=" + URLEncoder.encode(telefonoAlumno, "UTF-8")
                    + "&telefono_padre=" + URLEncoder.encode(telefonoPadre, "UTF-8")
                    + "&domicilio=" + URLEncoder.encode(domicilio, "UTF-8")
                    + "&no_exterior=" + URLEncoder.encode(noExterior, "UTF-8")
                    + "&no_interior=" + URLEncoder.encode(noInterior, "UTF-8")
                    + "&nombre_colonia=" + URLEncoder.encode(nombreColonia, "UTF-8")
                    + "&municipio=" + URLEncoder.encode(municipio, "UTF-8")
                    + "&estado_domicilio=" + URLEncoder.encode(estadoDomicilio, "UTF-8")
                    + "&codigo_postal=" + URLEncoder.encode(codigoPostal, "UTF-8")
                    + "&escuela_procedencia=" + URLEncoder.encode(escuelaProcedencia, "UTF-8")
                    + "&promedio=" + URLEncoder.encode(promedio, "UTF-8");

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(data);
            writer.flush();
            writer.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response += line;
                }
                if (response.equals("002")) {
                    response = "Solicitud enviada";
                } else if (response.equals("000")) {
                    response = "No se pudo enviar la solicitud";
                } else {
                    response += line;
                }
                reader.close();
            } else {
                response = "ERROR al procesar el servicio: " + connection.getResponseCode();
            }
            connection.disconnect();
        } catch (Exception ex) {
            response = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return response;
    }
    public String solicitarEvento(String fecha, String hora, String matricula, String nombre, String telefonocasa, String telefonocelular, String grupo, String evento) {
        String response = "";
        try {
            URL url = new URL("http://192.168.137.143:80/conexion_cecytem/solicitar_evento.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Construye los datos a enviar en el cuerpo de la solicitud
            String data = "fecha=" + URLEncoder.encode(fecha, "UTF-8")
                    + "&hora=" + URLEncoder.encode(hora, "UTF-8")
                    + "&matricula=" + URLEncoder.encode(matricula, "UTF-8")
                    + "&nombre=" + URLEncoder.encode(nombre, "UTF-8")
                    + "&telefonocasa=" + URLEncoder.encode(telefonocasa, "UTF-8")
                    + "&telefonocelular=" + URLEncoder.encode(telefonocelular, "UTF-8")
                    + "&grupo=" + URLEncoder.encode(grupo, "UTF-8")
                    + "&evento=" + URLEncoder.encode(evento, "UTF-8");

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(data);
            writer.flush();
            writer.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response += line;
                }
                if (response.equals("002")) {
                    response = "Solicitud enviada";
                } else if (response.equals("000")) {
                    response = "No se pudo enviar la solicitud";
                } else {
                    response += line;
                }
                reader.close();
            } else {
                response = "ERROR al procesar el servicio: " + connection.getResponseCode();
            }
            connection.disconnect();
        } catch (Exception ex) {
            response = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return response;
    }
    public String solicitarServicio(String hora, String fecha, String nombre, String promedio, String telefonocasa, String telefonocelular, String grupo, String correoins, String nombredepe, String cct, String turno, String domiciliode, String telefonodepe, String nivel, String nombreres, String cargores, String actividades, String horario, String nombrepadre) {

        String response = "";
        try {
            URL url = new URL("http://192.168.137.143:80/conexion_cecytem/solicitar_servicio.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Construye los datos a enviar en el cuerpo de la solicitud
            String data = "hora=" + URLEncoder.encode(hora, "UTF-8")
                    + "&fecha=" + URLEncoder.encode(fecha, "UTF-8")
                    + "&nombre=" + URLEncoder.encode(nombre, "UTF-8")
                    + "&promedio=" + URLEncoder.encode(promedio, "UTF-8")
                    + "&telefonocasa=" + URLEncoder.encode(telefonocasa, "UTF-8")
                    + "&telefonocelular=" + URLEncoder.encode(telefonocelular, "UTF-8")
                    + "&grupo=" + URLEncoder.encode(grupo, "UTF-8")
                    + "&correoins=" + URLEncoder.encode(correoins, "UTF-8")
                    + "&nombredepe=" + URLEncoder.encode(nombredepe, "UTF-8")
                    + "&cct=" + URLEncoder.encode(cct, "UTF-8")
                    + "&turno=" + URLEncoder.encode(turno, "UTF-8")
                    + "&domiciliode=" + URLEncoder.encode(domiciliode, "UTF-8")
                    + "&telefonodepe=" + URLEncoder.encode(telefonodepe, "UTF-8")
                    + "&nivel=" + URLEncoder.encode(nivel, "UTF-8")
                    + "&nombreres=" + URLEncoder.encode(nombreres, "UTF-8")
                    + "&cargores=" + URLEncoder.encode(cargores, "UTF-8")
                    + "&actividades=" + URLEncoder.encode(actividades, "UTF-8")
                    + "&horario=" + URLEncoder.encode(horario, "UTF-8")
                    + "&nombrepadre=" + URLEncoder.encode(nombrepadre, "UTF-8");

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(data);
            writer.flush();
            writer.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response += line;
                }
                if (response.equals("002")) {
                    response = "Solicitud enviada";
                } else if (response.equals("000")) {
                    response = "No se pudo enviar la solicitud";
                } else {
                    response += line;
                }
                reader.close();
            } else {
                response = "ERROR al procesar el servicio: " + connection.getResponseCode();
            }
            connection.disconnect();
        } catch (Exception ex) {
            response = "ERROR de SERVIDOR: " + ex.getMessage();
        }
        return response;
    }
}

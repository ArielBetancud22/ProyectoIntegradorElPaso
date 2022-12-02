
package gestor.el.paso;

import java.io.*;
import java.util.Scanner;

public class GestorElPaso {

    public static Scanner teclado = new Scanner(System.in);                                                             // Creamos un nuevo objeto Scanner.
    public static int i, j, op1, op2, op3, listado = 0, categoria = 4;                                                  // Al definir las variables que utilizaremos en el main
    public static int arregloLongitud[] = new int[999];                                                                 // establecemos valores definidos por defecto para 'listado'
    public static boolean codigo = false, mostrar = false;                                                              // y 'categoria'
    public static String matrizGeneral[][] = new String[999][999];

    // --------- SubProceso para agregar categorias --------------
    public static void agregarcategoria() {
        String desea;
        int i;
        do {
            // Como tenemos una matriz muy grande, y no queremos que se muestre lo que no esta lleno, proponemos como contador 
            // para el limite de uso la variable "categoria".
            categoria++;
            System.out.print("Nueva categoria: ");
            // Almacenamos siempre en la fila 0, que es la que contiene los encabezados.
            matrizGeneral[0][categoria] = teclado.nextLine();
            // Para los encabezados, convertimos a mayusculas.
            matrizGeneral[0][categoria] = matrizGeneral[0][categoria].toUpperCase();
            // Paralelamente, leemos el largo de la palabra ingresada para almacenarla en el arreglo, que nos sirve para mostrar
            // de forma ordenada la matriz en las siguientes subfunciones.
            arregloLongitud[categoria] = matrizGeneral[0][categoria].length();
            for (i = 1; i <= listado; i++) {
                matrizGeneral[i][categoria] = "";
            }
            System.out.println("");
            do {
                System.out.print("Desea agregar otra categoria? S/N");
                desea = teclado.nextLine();
                System.out.println("");
            } while (!(desea.toUpperCase().equals("N") || desea.toUpperCase().equals("S")));
        } while (!desea.toUpperCase().equals("N"));
        // Llenamos el resto de la columna con caracteres vacios, para evitar problemas con otras subfunciones.
    }

    // ---------------Subproceso para modificar encabezados -------------------
    // Aqui simplemente seleccionamos el valor de columna del encabezado a modificar, y sobreescribimos.
    public static void renombrar() {
        int columna;
        int i;
        // En este ciclo mostramos a modo de ayuda visual para el usuario, las columnas numeradas para su seleccion.
        for (i = 0; i <= categoria; i++) {
            if (i == categoria) {
                System.out.println(" " + matrizGeneral[0][i] + " (" + i + ")");
            } else {
                System.out.print(" " + matrizGeneral[0][i] + " (" + i + ")");
                System.out.print("|");
            }
        }
        System.out.println("");
        System.out.print("Indique la columna que desea renombrar:");
        // Con este ciclo protegemos de modificaciones a las columnas principales. 
        do {
            columna = teclado.nextInt();
            teclado.skip("\n");
            if (columna <= 4) {
                System.out.println("No se pueden modificar las columnas principales. Intentelo nuevamente.");
            }
        } while (columna <= 4);
        System.out.print("Ingrese el nuevo nombre:");
        matrizGeneral[0][columna] = teclado.nextLine();
        // Para los encabezados, convertimos a mayusculas.
        matrizGeneral[0][columna] = matrizGeneral[0][columna].toUpperCase();
        System.out.println("Nuevo nombre almacenado correctamente.");
        System.out.println("");
        // Notese que cada vez que se modifique un valor en la matrizPrincipal, se hace una sobreescritura o 
        // una comparacion con el arreglo que almacena el largo, para mostrar de manera ordenada la matriz.
        arregloLongitud[columna] = matrizGeneral[0][columna].length();
    }

    // -----------Subproceso para modificar eliminando una columna ----------
    public static void eliminarcolumna() {
        int elegida, i, j;
        // En este ciclo mostramos a modo de ayuda visual para el usuario las columnas numeradas para su seleccion.
        for (i = 0; i <= GestorElPaso.categoria; i++) {
            if (i == categoria) {
                System.out.println(" " + matrizGeneral[0][i] + " (" + i + ")");
            } else {
                System.out.print(" " + matrizGeneral[0][i] + " (" + i + ")");
                System.out.print("|");
            }
        }
        System.out.println("");
        // Aqui seleccionamos el valor de columna a eliminar, y para que no quede el espacio vacio, traemos todas las columnas que estan
        // a continuacion para ocupar ese lugar.
        // Con este ciclo limitamos la seleccion para no poder eliminar las columnas principales, ya que seran usadas en otros subprocesos.
        do {
            System.out.println("Ingrese la columna a eliminar.");
            teclado.skip("\n");
            elegida = teclado.nextInt();
            // Mensaje de error.
            if (elegida <= 4) {
                System.out.println("No se pueden modificar las columnas principales. Intentelo nuevamente.");
            }
        } while (elegida <= 4);
        // Aqui hacemos el ciclo para reescribir los valores de largo de palabra en el arreglo correspondiente.
        for (j = elegida; j <= categoria - 1; j++) {
            arregloLongitud[j] = arregloLongitud[j + 1];
        }
        // Aqui reescribimos todas las columnas que se encuentran a continuacion de la eliminada.
        for (i = 0; i <= listado; i++) {
            for (j = elegida; j <= categoria - 1; j++) {
                matrizGeneral[i][j] = matrizGeneral[i][j + 1];
            }
        }
        // Si se ingresara una columna no existente se volveria al menu principal sin realizar ningun cambio.
        if (elegida > 4 && elegida <= categoria) {
            categoria--;
            System.out.println("Columna eliminada correctamente.");
            System.out.println("");
        }
    }

    // ---------- Subproceso para cargar stock en la matriz general ---------
    public static void cargarstock() {
        String desea;
        int j;
        // En este ciclo se cargan los articulos, uno a uno.
        do {
            // Al igual que la variable "categoria", la variable "listado" nos sirve para establecer un limite de muestra, pero esta
            // vez en relacion a las filas.
            listado++;
            System.out.println("Ingrese los datos:");
            for (j = 0; j <= categoria; j++) {
                // En este condicional, establecemos que NO SE MODIFIQUE la columna 0, que almacena los codigos. Estos seran generados
                // automaticamente por el programa. 
                if (j == 0) {
                    if (listado < 100) {
                        matrizGeneral[listado][j] = "0".concat(Integer.toString(listado));
                        if (listado < 10) {
                            matrizGeneral[listado][j] = "00".concat(Integer.toString(listado));
                        }
                    }
                    System.out.println(matrizGeneral[0][j] + ": " + matrizGeneral[listado][j]);
                } else {
                    // Aca recorremos la fila, completando los datos requeridos.
                    System.out.print(matrizGeneral[0][j] + ": ");
                    matrizGeneral[listado][j] = teclado.nextLine();
                    // Reescribimos con la primer letra en mayuscula y el resto en minuscula, para una visualizacion mas agradable.
                    matrizGeneral[listado][j] = (matrizGeneral[listado][j].substring(0, 1).toUpperCase()) + (matrizGeneral[listado][j].substring(1, matrizGeneral[listado][j].length()).toLowerCase());
                    if (matrizGeneral[listado][j].length() > arregloLongitud[j]) {
                        arregloLongitud[j] = matrizGeneral[listado][j].length();
                    }
                    matrizGeneral[listado][100] = "0";
                }
            }
            System.out.println("");
            do {
                System.out.println("�Desea ingresar un nuevo articulo? S/N");
                desea = teclado.nextLine();
            } while (!(desea.toUpperCase().equals("S") || desea.toUpperCase().equals("N")));
        } while (!desea.toUpperCase().equals("N"));
        System.out.println("");
    }

    // ---------------  SubProceso para eliminar un articulo. ---------------
    // Del listado mostrado ponemos elegimos uno de los productos y eliminamos el deseado, reescribiendo la lista para mostrar el listado definitivo
    public static void eliminarproducto() throws IOException {
        int elegido, i, j;
        do {
            System.out.println("Ingrese el producto a eliminar.");
            elegido = teclado.nextInt();
            // Mensaje de error.
            if (elegido <= 0) {
                System.out.println("Producto inexistente. Intentelo nuevamente.");
            }
        } while (elegido <= 0);
        // Se reescriben los productos a continuaci�n del eliminado
        for (i = elegido; i <= listado - 1; i++) {
            for (j = 0; j <= categoria; j++) {
                if (j == 0) {
                    // Asumimos un valor de 3 digitos.
                    matrizGeneral[i][j] = Integer.toString(Integer.valueOf(matrizGeneral[i + 1][j]) - 1);
                    if (i < 100) {
                        // Completamos con un cero para 2 digitos.
                        matrizGeneral[i][j] = "0".concat(Integer.toString(Integer.valueOf(matrizGeneral[i + 1][j]) - 1));
                        if (i < 10) {
                            // Completamos con dos ceros para 1 digito.
                            matrizGeneral[i][j] = "00".concat(Integer.toString(Integer.valueOf(matrizGeneral[i + 1][j]) - 1));
                        }
                    }
                } else {
                    matrizGeneral[i][j] = matrizGeneral[i + 1][j];
                }
            }
        }
        // Se vuelve al men� principal si ingresamos un c�digo distinto
        if (elegido > 0 && elegido <= listado) {
            listado--;
            System.out.println("Producto eliminado correctamente.");
            System.out.println("");
        }
    }

    // --------------------- SubProceso modificar stock ----------------------
    public static void modificarstock() {
        String reescribir, desea = "";
        int i, codigo;
        System.out.println("");
        do {
            System.out.print("Ingrese el codigo del articulo o presione 0 para volver.");
            System.out.println("");
            do {
                codigo = teclado.nextInt();
                teclado.skip("\n");
                if (codigo > listado || codigo < 0) {
                    // Mensaje de error.
                    System.out.println("No hay articulos almacenados en ese codigo. Intentelo nuevamente.");
                }
            } while (codigo > listado || codigo <= 0);
            System.out.println("");
            if (codigo != 0) {
                System.out.println("Ingrese el nuevo valor o ingrese 0 para omitir.");
                for (i = 1; i <= categoria; i++) {
                    System.out.print(matrizGeneral[0][i] + ": ");
                    reescribir = teclado.nextLine();
                    if (!reescribir.equals("0")) {
                        matrizGeneral[codigo][i] = reescribir;
                    }
                }
                matrizGeneral[codigo][1] = (matrizGeneral[codigo][1].substring(0, 1).toUpperCase()).concat(matrizGeneral[codigo][1].substring(1, matrizGeneral[codigo][1].length()).toLowerCase());
                System.out.println("");
                do {
                    System.out.println("�Desea modificar otro articulo? S/N");
                    desea = teclado.nextLine();
                } while (!(desea.toUpperCase().equals("S") || desea.toUpperCase().equals("N")));
            }
        } while (!(desea.toUpperCase().equals("N") || codigo == 0));
    }

    // Subproceso para mostrar la matriz.
    public static void mostrarmatriz() {
        int diferencia, has = listado, pas = 1, num, i, j, k;
        if (codigo == true) {
            do {
                System.out.println("Ingrese el codigo o presione 0 para volver:");
                num = teclado.nextInt();
                if (num > listado || num < 0) {
                    System.out.println("No hay articulos almacenados en ese codigo. Intentelo nuevamente.");
                }
            } while (!(num <= listado && num >= 0));
            pas = num;
            has = num;
        }
        // Recorremos el arreglo mostrando los valores almacenados.
        for (i = 0; i <= has; i += pas) {
            for (j = 0; j <= categoria; j++) {
                // Como estamos en la ultima categoria, no ponemos barrita al final.
                if (j == categoria) {
                    // Comparamos la palabra mas larga de cada columna, para acomodar la visualizacion respecto a esta.
                    // La diferencia puede ir aplicada como subproceso. 
                    diferencia = arregloLongitud[j] - matrizGeneral[i][j].length();
                    System.out.print(" " + matrizGeneral[i][j] + " ");
                    // Al haber calculado cuantos espacios hay de diferencia, rellenamos con espacios vacios.
                    for (k = 1; k <= diferencia; k++) {
                        System.out.print(" ");
                    }
                } else {
                    diferencia = arregloLongitud[j] - matrizGeneral[i][j].length();
                    System.out.print(" " + matrizGeneral[i][j] + " ");
                    for (k = 1; k <= diferencia; k++) {
                        System.out.print(" ");
                    }
                    System.out.print("|");
                }
            }
            System.out.println("");
        }
        codigo = false;
    }

    // ------------------- Subproceso para registrar venta --------------------
    public static void registrarventa() throws IOException, InterruptedException {
        String desea = "", vendido;
        int diferencia, i, k, codigo;
        double total = 0;
        // Para cada venta, necesitamos un registro que se renueve para no sobreescribir un ticket nuevo sobre otro.
        // Mediante esta escritura, cada vez que entremos al comando de registrarVenta, seteamos esa lista en cero.
        for (i = 1; i <= listado; i++) {
            matrizGeneral[i][101] = "0";
        }
        // Iniciamos la busqueda de articulo por codigo. 
        System.out.println("Ingrese por codigo los articulos vendidos o presione 0 para volver.");
        do {
            do {
                do {
                    System.out.print("COD: ");
                    codigo = teclado.nextInt();
                    teclado.skip("\n");
                    // Limitamos el ciclo a la cantidad de articulos existentes unicamente. 
                    if (codigo > listado || codigo < 0) {
                        // Mensaje de error.
                        System.out.println("No hay articulos almacenados en ese codigo. Intentelo nuevamente.");
                    }
                } while (codigo > listado || codigo <= 0);
                if (codigo != 0) {
                    System.out.print(matrizGeneral[codigo][1] + " - CANTIDAD: ");
                    // Utilizamos este ciclo para limitar lo vendido segun el stock registrado.
                    do {
                        vendido = teclado.nextLine();
                        // Mensaje de error
                        if (Integer.valueOf(vendido) > Integer.valueOf(matrizGeneral[codigo][2])) {
                            System.out.println("ATENCION! No cuenta con esa cantidad en stock. Stock restante: " + matrizGeneral[codigo][2]);
                            System.out.println("Intentelo nuevamente.");
                        }
                    } while (Integer.valueOf(vendido) > Integer.valueOf(matrizGeneral[codigo][2]));
                    // Almacenamos la cantidad vendida de cada articulo en una columna lejana de la matriz para, posteriormente, usar esos datos
                    // para calcular la ganancia, a la vez que descontamos lo vendido del stock total.
                    // Al salir del loop, modifica la matriz general y almacena la cantidad vendida.
                    matrizGeneral[codigo][2] = Double.toString(Integer.valueOf(matrizGeneral[codigo][2]) - Integer.valueOf(vendido));
                    matrizGeneral[codigo][100] = Double.toString(Integer.valueOf(matrizGeneral[codigo][100]) + Integer.valueOf(vendido));
                    if (Integer.valueOf(matrizGeneral[codigo][101]) == 0) {
                        matrizGeneral[codigo][101] = vendido;
                    } else {
                        matrizGeneral[codigo][101] = Double.toString(Integer.valueOf(matrizGeneral[codigo][101]) + Integer.valueOf(vendido));
                    }
                    // Comprobacion de salida.
                    do {
                        System.out.println("�Desea ingresar otro articulo? S/N");
                        desea = teclado.nextLine();
                    } while (!(desea.toUpperCase().equals("S") || desea.toUpperCase().equals("N")));
                } else {
                    // Esto correponde a elegir 0, que implica cancelar el ticket actual, por lo que devolvemos los valores modificados a su situacion anterior.
                    // funciona a modo de "des-hacer"
                    for (i = 1; i <= listado; i++) {
                        matrizGeneral[i][2] = Double.toString(Integer.valueOf(matrizGeneral[i][2]) + Integer.valueOf(matrizGeneral[i][100]));
                        matrizGeneral[i][100] = "0";
                    }
                }
            } while (!(desea.toUpperCase().equals("N")));
            // Imprimimos en pantalla los datos de la venta. Cantidad, precio unitario, precio total.
            for (i = 1; i <= listado; i++) {
                if (Integer.valueOf(matrizGeneral[i][101]) > 0) {
                    System.out.print("COD " + matrizGeneral[i][0] + " - " + matrizGeneral[i][1]);
                    diferencia = arregloLongitud[1] - matrizGeneral[i][1].length();
                    for (k = 1; k <= diferencia; k++) {
                        System.out.print(" ");
                    }
                    System.out.print(" - " + matrizGeneral[i][101] + " x $" + matrizGeneral[i][4] + " = $" + Integer.valueOf(matrizGeneral[i][101]) * Integer.valueOf(matrizGeneral[i][4]));
                    total = total + Double.valueOf(matrizGeneral[i][101]) * Double.valueOf(matrizGeneral[i][4]);
                    System.out.println("");
                }
            }
            // Rellenamos espacios para una presentacion mas agradable.
            System.out.print("                ");
            for (k = 1; k <= arregloLongitud[1]; k++) {
                System.out.print(" ");
            }
            System.out.println("TOTAL = $" + total);
            System.out.println("");
        } while (!(desea.toUpperCase().equals("N") || codigo == 0));
        System.out.println("Presione ENTER para regresar al menu principal.");
        System.in.read();
    }

    // ------------ Subproceso para calcular la ganancia del dia -------------
    public static void calcularganancia() throws IOException {
        double ganancia = 0;                                                // Seteamos la variable que almacenara nuestras ganancias en 0.
        int i;
        // Recorremos todas las filas, utilizando las columnas de vendido, costo y precio final para calcular el total.
        for (i = 1; i <= listado; i++) {
            ganancia += (Double.valueOf(matrizGeneral[i][100]) * Double.valueOf(matrizGeneral[i][4]) - Double.valueOf(matrizGeneral[i][100]) * Double.valueOf(matrizGeneral[i][3]));
        }
        // Devolvemos el mensaje con el total del dia.
        System.out.println("La ganancia total de hoy es: $" + ganancia);
        System.out.println("");
        System.out.println("Presione ENTER para regresar al menu principal.");
        System.in.read();
    }

    // ---------------- SubProceso cantidad de coincidencias -----------------
    public static int cantcoin(int listado, int categoria, String matrizGeneral[][], String string) {
        boolean coincidencia;
        int i, j, numDeCoin;
        numDeCoin = 0;
        for (i = 1; i <= listado; i++) {
            coincidencia = false;
            for (j = 0; j <= (matrizGeneral[i][1].length() - string.length()); j++) {
                if (string.toLowerCase().equals(matrizGeneral[i][1].substring(j, (j + string.length() - 1) + 1).toLowerCase()) && coincidencia == false || (i == 0 && j == 0)) {
                    coincidencia = true;
                    numDeCoin++;
                }
            }
        }
        return numDeCoin;
    }

    // ---------------- Subproceso para menu de busqueda ---------------------
    public static void busqueda() throws IOException {
        int seleccion;
        if (listado == 0) {
            System.out.println("Aun no hay articulos almacenados en la lista.");
        } else {
            System.out.println("Ingrese el parametro de busqueda: ");
            System.out.println("1 - Por Codigo.");
            System.out.println("2 - Por Texto.");
            seleccion = teclado.nextInt();
            teclado.skip("\n");
            switch (seleccion) {
                case 1:
                    codigo = true;
                    mostrarmatriz();
                    break;
                case 2:
                    busquedastring();
                    break;
            }
        }
        System.out.println("Presione ENTER para regresar al menu principal.");
        System.in.read();
    }

    // -------------------- Subproceso busqueda por string --------------------
    public static void busquedastring() throws IOException {
        boolean bandera = false, soloUno, coincidencia;
        int diferencia, i, j, k, m;
        String string;
        System.out.println("Ingrese el texto:");
        string = teclado.nextLine();
        if (cantcoin(listado, categoria, matrizGeneral, string) == 0) {
            System.out.println("************************************************************");
            System.out.println("                   NO HAY COINCIDENCIAS.                    ");
            System.out.println("************************************************************");
        } else {
            if (cantcoin(listado, categoria, matrizGeneral, string) == 1) {
                System.out.println("Su busqueda ha dado el siguiente resultado: ");
                System.out.println("");
                soloUno = true;
            } else {
                System.out.println("Su busqueda ha dado los siguientes resultados (" + cantcoin(listado, categoria, matrizGeneral, string) + "):");
                System.out.println("");
            }

            for (i = 0; i <= listado; i++) {
                coincidencia = false;
                for (j = 0; j <= (matrizGeneral[i][1].length() - string.length()); j++) {
                    if (string.toLowerCase().equals(matrizGeneral[i][1].substring(j, (j + string.length() - 1) + 1).toLowerCase()) && coincidencia == false || (i == 0 && j == 0)) {
                        coincidencia = true;
                        if (coincidencia == true && i != 0) {
                            bandera = true;
                        }
                        for (k = 0; k <= categoria; k++) {
                            if (k == categoria) {
                                // Comparamos la palabra mas larga de cada columna, para acomodar la visualizacion respecto a esta.
                                // La diferencia puede ir aplicada como subproceso. 
                                diferencia = arregloLongitud[k] - matrizGeneral[i][k].length();
                                System.out.print(" " + matrizGeneral[i][k] + " ");
                                // Al haber calculado cuantos espacios hay de diferencia, rellenamos con espacios vacios.
                                for (m = 1; m <= diferencia; m++) {
                                    System.out.print(" ");
                                }
                            } else {
                                diferencia = arregloLongitud[k] - matrizGeneral[i][k].length();
                                System.out.print(" " + matrizGeneral[i][k] + " ");
                                for (m = 1; m <= diferencia; m++) {
                                    System.out.print(" ");
                                }
                                System.out.print("|");
                            }
                        }
                        System.out.println("");
                    }
                }
            }
            if (bandera == false) {
                System.out.println("");
                System.out.println("                    NO HAY COINCIDENCIAS.");
                System.out.println("");
            } else {
                System.out.println("");
            }
        }
    }

    // ---------------- Proceso reordenar alfabeticamente ------------------
    public static void reordenaralf() {
        String aux;
        int i, j, k;
        for (k = 0; k <= listado; k++) {
            for (i = 1; i <= listado - 1; i++) {
                if (matrizGeneral[i][1].compareTo(matrizGeneral[i + 1][1]) > 0) {
                    for (j = 1; j <= categoria; j++) {
                        aux = matrizGeneral[i][j];
                        matrizGeneral[i][j] = matrizGeneral[i + 1][j];
                        matrizGeneral[i + 1][j] = aux;
                    }
                }
            }
        }
    }

    // --------- Proceso Principal ------------
    public static void main(String[] args) throws IOException, InterruptedException {

        matrizGeneral[0][0] = "COD";
        matrizGeneral[0][1] = "ARTICULO";
        matrizGeneral[0][2] = "CANTIDAD";
        matrizGeneral[0][3] = "COSTO ($)";
        matrizGeneral[0][4] = "PRECIO DE VENTA ($)";

        for (j = 0; j <= categoria; j++) {                                                // Almacenamos los largos de cada encabezado para compararlos luego con los de los articulos ingresados. Si
            arregloLongitud[j] = matrizGeneral[0][j].length();                      // ingresara un articulo mas largo, la columna se acomodaria a este. 
        }

        System.out.println("********** BIENVENIDOS AL SUPERGESTOR 3000!!! **********");
        System.out.println("Por defecto le presentamos las siguientes categorias, puede agregar otras si usted desea. ");

        for (i = 0; i <= 0; i++) {                                                        // Mostramos la matriz almacenada. Hasta el momento solo contiene
            for (j = 0; j <= categoria; j++) {                                            // los encabezados
                if (j == categoria) {
                    System.out.print(matrizGeneral[i][j]);
                } else {
                    System.out.print(matrizGeneral[i][j] + " |" + " ");
                }
            }
            System.out.println("");
        }

        System.out.println("Presione ENTER para continuar.");
        System.in.read();

        // -------------- Presentamos el menu principal ----------------------
        do {
            if (mostrar == true) {
                mostrarmatriz();
                System.out.println("");
            }
            System.out.println("   Digite el n�mero correspondiente a la opci�n deseada: ");
            System.out.println("");
            System.out.println("   1 - Categoria.");
            System.out.println("   2 - Stock.");
            if (mostrar == true) {
                System.out.println("   3 - Ocultar Listado.");
            } else {
                System.out.println("   3 - Mostrar Listado.");
            }
            System.out.println("   4 - Reordenar Alfabeticamente.");
            System.out.println("   5 - Buscar.");
            System.out.println("   6 - Registrar Venta.");
            System.out.println("   7 - Calcular Ganancia.");
            System.out.println("   8 - Salir.");
            System.out.println("");
            System.out.print("Opcion: ");
            op1 = teclado.nextInt();
            teclado.skip("\n");
            System.out.println("");
            switch (op1) {
                case 1 -> {
                    do {
                        System.out.println("Elija una opcion:");
                        System.out.println("");
                        System.out.println("1 - Agregar.");
                        System.out.println("2 - Renombrar.");
                        System.out.println("3 - Eliminar.");
                        System.out.println("4 - Volver.");
                        op2 = teclado.nextInt();
                        teclado.skip("\n");
                        switch (op2) {
                            case 1 ->
                                agregarcategoria();
                            case 2 ->
                                renombrar();
                            case 3 ->
                                eliminarcolumna();
                        }
                        if (op2 == 4) {
                            op1 = 20;
                        }
                    } while (op2 != 4);
                }
                case 2 -> {
                    do {
                        System.out.println("   Elija una opcion: ");
                        System.out.println("");
                        System.out.println("   1 - Agregar Producto.");
                        System.out.println("   2 - Modificar Producto.");
                        System.out.println("   3 - Eliminar Producto");
                        System.out.println("   4 - Volver.");
                        op3 = teclado.nextInt();
                        teclado.skip("\n");
                        switch (op3) {
                            case 1 ->
                                cargarstock();
                            case 2 ->
                                modificarstock();
                            case 3 -> {
                                mostrarmatriz();
                                eliminarproducto();
                            }
                            default -> {
                            }
                        }
                    } while (op3 != 4);
                }
                case 3 -> {
                    // A modo de switch presentamos la muestra permanente durante el menu de el listado en su estado actual.
                    mostrar = mostrar != true;
                }
                case 4 ->
                    reordenaralf();
                case 5 ->
                    busqueda();
                case 6 ->
                    registrarventa();
                case 7 ->
                    calcularganancia();
                default -> {
                }
            }
        } while (op1 != 8);                                                     // Opcion de salida.
    }
}

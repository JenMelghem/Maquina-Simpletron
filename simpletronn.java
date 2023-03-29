import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class simpletronn {
  public static void main(String[] args) {
    int[] memory = new int[1000];
            int acumulador = 0;
            int contadorDeInstrucciones = 0;
            int codigoDeOperacion = 0;
            int operando = 0;
            int registroDeInstruccion = 0;

           System .out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("* Bienvenido a Simpletron! *");

    try (Scanner input = new Scanner(System.in)) {
        System.out.println("¿Desea ingresar instrucciones manualmente o cargar un archivo txt? (M/C)");
        String opcion = input.next();

        if (opcion.equalsIgnoreCase("M")) {
          System.out.println("* Por favor, introduzca en su programa una instruccion *");
          System.out.println("* (o palabra de datos) a la vez. Yo le mostrare *");
          System.out.println("* el numero de ubicacion y un signo de interrogacion (?) *");
          System.out.println("* Entonces usted escribira la palabra para esa ubicacion. *");
          System.out.println("* Teclee -999 para dejar de introducir su programa. *");
          

          // Carga del programa en memoria
int instruccion = 0;
do {
    System.out.printf("%02d ? ", contadorDeInstrucciones);
    instruccion = input.nextInt();
    if (instruccion == -999) {
        break;
    }
    memory[contadorDeInstrucciones] = instruccion;
    contadorDeInstrucciones++;

} while (contadorDeInstrucciones < 1000 && instruccion != -999);

System.out.println("* Se completo la carga del programa *");
System.out.println("* Empieza la ejecucion del programa *");

    {     
         contadorDeInstrucciones = 0;
        while (codigoDeOperacion != 43) {
            registroDeInstruccion = memory[contadorDeInstrucciones];
            codigoDeOperacion = registroDeInstruccion / 1000;
            operando = registroDeInstruccion % 1000;
                
        
            switch (codigoDeOperacion) {
                case 10: // Leer
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    
                    System.out.print("Ingrese un entero: ");
                    int numero = input.nextInt();
                    memory[operando] = numero;
                    break;
                    case 11: // Escribir
                    System.out.printf("El contenido de la ubicacion %02d es %d%n", operando, memory[operando]);
                    break;
                    
                    case 12: // Nueva linea
                    System.out.println(); // Agregar una nueva linea
                    break;
                    
                case 13: // Leer cadena
                    System.out.print("Ingrese una cadena: ");
                    String cadena = input.nextLine();
                    int longitudCadena = cadena.length();
                    memory[operando] = longitudCadena;
                    for (int i = 0; i < longitudCadena; i++) {
                        char caracter = cadena.charAt(i);
                        int codigoASCII = (int) caracter;
                        int mediaPalabra = (codigoASCII / 100) * 100 + (codigoASCII % 100);
                        memory[operando + 1 + i] = mediaPalabra;
                    }
                    break;
                    
                    case 14: // Imprimir cadena
                    int direccion = operando;
                    int longitud = memory[direccion] / 1000; // La longitud está almacenada en la primera mitad de la palabra
                    System.out.print("La cadena es: ");
                    for (int i = 0; i < longitud; i++) {
                        int codigoAscii = memory[direccion + i + 1] % 1000; // Cada media palabra subsiguiente contiene un carácter ASCII
                        char caracter = (char) codigoAscii;
                        System.out.print(caracter);
                    }
                    System.out.println();
                    break;
                
                    case 20: // Cargar
                    acumulador = memory[operando];
                    break;
                    case 21: // Almacenar
                    memory[operando] = acumulador;
                    break;
                    case 30: // Sumar
                    acumulador += memory[operando];
                    if (acumulador > 9999 || acumulador < -9999) {
                        System.out.println("* ERROR: Desbordamiento del acumulador *");
                        System.exit(1);
                    }
                    break;
                    case 31: // Restar
                    acumulador -= memory[operando];
                    if (acumulador > 9999 || acumulador < -9999) {
                        System.out.println("* ERROR: Desbordamiento del acumulador *");
                        System.exit(1);
                    }
                    break;
                    case 32: // Dividir
                    if (memory[operando] == 0) {
                        System.out.println("*** ERROR: Division por cero ***");
                        System.exit(1);
                    } else {
                        acumulador /= memory[operando];
                    }
                    break;
                    
                    case 33: // Multiplicar
                    acumulador *= memory[operando];
                    if (acumulador > 9999 || acumulador < -9999) {
                        System.out.println("* ERROR: Desbordamiento del acumulador *");
                        System.exit(1);
                    }
                    break;
                
                    
                    case 34: // Dividir y obtener residuo

                    // Obtener el divisor de la memoria y verificar que no sea cero
                    int divisor = memory[operando];
                    if (divisor == 0) {
                        System.out.println("* ERROR: División por cero *");
                        System.exit(1);
                    }
                    // obtiene el residuo y el cociente de la divison 
                    int residuo = acumulador % divisor;
                    acumulador /= divisor;

                    // Guardar el residuo en la memoria
                    memory[operando] = residuo;
                    // Imprimir el valor del residuo
                    System.out.println("El residuo es: " + residuo);
                    break;
                
                    
                    case 35: // Exponenciacion
                        acumulador = (int) Math.pow(acumulador, memory[operando]);

                    
                            case 40: // Bifurcar
                                if (operando < 0 || operando > 9999) {
                                    System.out.println("*** ERROR: Dirección de memoria inválida ***");
                                    System.exit(1);
                                } else {
                                        acumulador = operando;
                                }
                                break;
                            
                     case 41: // Bifurcar si negativo
                        if (acumulador < 0) {
                             int dire = memory[operando];
                                if (dire < 0 || dire >= memory.length) {
                                 System.out.println("*** ERROR: Direccion de memoria invalida ***");
                                 System.exit(1);
                                
                                } else {
                                        acumulador = dire;
                                    }
                                }
                                break;
                            
                }
                contadorDeInstrucciones++;
                }
            } 
            }
            
            else if (opcion.equalsIgnoreCase("C")) {
                System.out.println("Ingrese la ruta del archivo:");
                String rutaArchivo = input.next();
            
                ArrayList<Integer> instrucciones = new ArrayList<Integer>();
            
                try {
                    File archivo = new File(rutaArchivo);
                    Scanner lector = new Scanner(archivo);
                
                    while (lector.hasNextLine()) {
                        String linea = lector.nextLine();
                        try {
                            int instruccion = Integer.parseInt(linea);
                            instrucciones.add(instruccion); // 
                        } catch (NumberFormatException e) {
                            System.out.println("La línea \"" + linea + "\" no es un número entero válido.");
                        }
                    }
                
                    lector.close();
                } 
                catch (FileNotFoundException e) 
                {
                    System.out.println("El archivo no existe.");
                }
            
            
                
                // instrucciones y ejecutarlas
                while (codigoDeOperacion != 43 && contadorDeInstrucciones < instrucciones.size()) {
                registroDeInstruccion = instrucciones.get(contadorDeInstrucciones);
                codigoDeOperacion = registroDeInstruccion / 1000;
                operando = registroDeInstruccion % 1000;
            
                switch (codigoDeOperacion) {
                    case 10: // Leer
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        
                        System.out.print("Ingrese un entero: ");
                        int numero = input.nextInt();
                        memory[operando] = numero;
                        break;
                        case 11: // Escribir
                        System.out.printf("El contenido de la ubicacion %02d es %d%n", operando, memory[operando]);
                        break;
                        
                        case 12: // Nueva linea
                        System.out.println(); // Agregar una nueva linea
                        break;
                        
                        case 13: // Almacenar cadena
                        System.out.print("Ingrese ua cadena: ");
                        String cadena0 = input.next();
                        int adress0 = operando;
                        for (int i = 0; i < cadena0.length(); i++) {
                            char caracter = cadena0.charAt(i);
                            int ascii = (int) caracter;
                            memory[adress0 + i] = ascii;
                        }
                        memory[adress0 + cadena0.length()] = 0; // Agregar un caracter nulo al final de la cadena
                        break;
                        
                    case 14: // Imprimir cadena
                        int address1 = operando;
                        int length = (int) (memory[address1] / 2);
                        address1++;
                        String output0 = "";
                        for (int i = 0; i < length; i++) {
                            int ascii = (int) memory[address1];
                            char character = (char) ascii;
                            output0 += character;
                            address1++;
                        }
                        System.out.println(output0);
                        break;
                    
                        case 20: // Cargar
                        acumulador = memory[operando];
                        break;
                        case 21: // Almacenar
                        memory[operando] = acumulador;
                        break;
                        case 30: // Sumar
                        acumulador += memory[operando];
                        if (acumulador > 9999 || acumulador < -9999) {
                            System.out.println("* ERROR: Desbordamiento del acumulador *");
                            System.exit(1);
                        }
                        break;
                        case 31: // Restar
                        acumulador -= memory[operando];
                        if (acumulador > 9999 || acumulador < -9999) {
                            System.out.println("* ERROR: Desbordamiento del acumulador *");
                            System.exit(1);
                        }
                        break;
                        case 32: // Dividir
                        if (memory[operando] == 0) {
                            System.out.println("*** ERROR: Division por cero ***");
                            System.exit(1);
                        } else {
                            acumulador /= memory[operando];
                        }
                        break;
                        
                        case 33: // Multiplicar
                        acumulador *= memory[operando];
                        if (acumulador > 9999 || acumulador < -9999) {
                            System.out.println("* ERROR: Desbordamiento del acumulador *");
                            System.exit(1);
                        }
                        break;
                    
                        
                        case 34: // Dividir y obtener residuo

                        // Obtener el divisor de la memoria y verificar que no sea cero
                        int divisor = memory[operando];
                        if (divisor == 0) {
                            System.out.println("* ERROR: División por cero *");
                            System.exit(1);
                        }
                        // obtiene el residuo y el cociente de la divison 
                        int residuo = acumulador % divisor;
                        acumulador /= divisor;

                        // Guardar el residuo en la memoria
                        memory[operando] = residuo;
                        // Imprimir el valor del residuo
                        System.out.println("El residuo es: " + residuo);
                        break;
                    
                        
                        case 35: // Exponenciacion
                            acumulador = (int) Math.pow(acumulador, memory[operando]);

                        
                                case 40: // Bifurcar
                                    if (operando < 0 || operando > 9999) {
                                        System.out.println("*** ERROR: Dirección de memoria inválida ***");
                                        System.exit(1);
                                    } else {
                                            acumulador = operando;
                                    }
                                    break;
                                
                         case 41: // Bifurcar si negativo
                            if (acumulador < 0) {
                                 int dire = memory[operando];
                                    if (dire < 0 || dire >= memory.length) {
                                     System.out.println("*** ERROR: Direccion de memoria invalida ***");
                                     System.exit(1);
                                    
                                    } else {
                                            acumulador = dire;
                                        }
                                    }
                                    break;
                                
                    }
                        contadorDeInstrucciones++;
                
                }
            }  
        }
    }
}
# Mutants

Proyecto que permite detectar si un humano es un mutante basándose en su secuencia de ADN.

## Servicios

### /mutant

Servicio HTTP POST que permite detectar si un humano es un mutante a partir de la secuencia de ADN. Almacena el resultado del análisis en la Base de Datos.
Se considera que una secuencia de ADN pertenece a un mutante si se encuentra más de una coincidencia de secuencias de **cuatro letras iguales seguidas**, de forma horizontal, vertical o diagonal.

|| | | | ↙ | |↓|
|------| ------ | ------ | ------ | ------ |------ | ------ |
||A|C|T|**G**|C|**C**|
||A|C|**G**|G|A|**C**|
||A|**G**|T|G|T|**C**|
||**G**|C|G|T|A|**C**|
|→|C|A|**T**|**T**|**T**|**T**|


> POST: `/mutant`
#### Parámetros

| Nombre | Tipo | Descripción |
| ------ | ------ | ------ |
| dna | String[ ] | Secuencia de ADN a analizar

###### dna
- Cada elemento del arreglo representa una fila de una tabla de [NxN] con la secuencia del ADN. 
- El arreglo debe contener mínimo 4 elementos y máximo 38.
- Cada elemento del arreglo debe tener una cantidad de caracteres igual al número de elementos que contenga el arreglo.
- Los caracteres del arreglo solo pueden ser las siguientes letras: "A", "T", "C", "G".

##### Ejemplo
```sh
{
    "dna": [
        "ATGCGA",
        "CAGTGC",
        "TTATGT",
        "AGAAGG",
        "CCCCTA",
        "TCACTG"
    ]
}
```

#### Respuesta

| Código | Descripción | Ejemplo |
| ------ | ------ | ------ |
| 200 | El ADN es de mutante | `{ "mutant": true }` |
| 403 | El ADN no es de mutante | `{ "mutant": false }` |
| 400 | Parámetro de entrada inválido| `{ "message": "Error en la estructura del mensaje de entrada: verifique e intente nuevamente", "httpStatus": "BAD_REQUEST" }` |

### /stats

Servicio HTTP GET que permite obtener las estadísticas de verificaciones de las secuencias de ADN que han sido analizados con el servicio "/mutant".


> GET: `/stats`
#### Respuesta

| Código | Descripción | Ejemplo |
| ------ | ------ | ------ |
| 200 | Estadísticas de verificaciones de ADN | `{"count_mutant_dna": 3,    "count_human_dna": 3,    "ratio": "1.0"}` |

- count_mutant_dna: Cantidad de mutantes detectados.
- count_human_dna: Cantidad de no mutantes (humanos) detectados.
- ratio: Relación cuantitativa entre la cantidad de mutantes y la cantidad de humanos. Si la cantidad de humanos es 0, el ratio se considera indefinido ("undefined")

## Configuración

- Lenguaje: Java 11
- Framework: Springboot
- Base de Datos: Cloud Firestore (Google)

### Ejecución local

1. Descargar o clonar el proyecto.
2. Crear el archivo de credenciales de GCP en: `/resources/firestorekeys.json` (Ver mas adelante la sección **Archivo de credenciales de Google Cloud Platform (GCP)**).
3. Importar o abrir el proyecto con el IDE de su preferencia.
4. Iniciar la ejecución del programa con Spring Boot.

### Variables de entorno

Las variables de entorno se configuran dentro del archivo `/resources/application.properties`

| Variable | Descripción | Valor por defecto |
| ------ | ------ | ------ |
| `app.sequence.length` | Número de letras iguales seguidas que se deben encontrar para considerar una coincidencia de secuencia de ADN. Determina el tamaño mínimo del arreglo de entrada "dna" y la longitud de cada uno de sus elementos | 4 |
| `app.min.coincidence` | Número de coincidencias que se deben encontrar para considerar que el ADN es de un mutante | 2 |
| `app.max.matriz.size` | Determina el tamaño máximo del arreglo de entrada "dna" y la longitud de cada uno de sus elementos | 38 |

Por defecto el tamaño máximo del arreglo de entrada es de 38 debido a que la Base de Datos Firestore tiene un límite de almacenamiento de 1500 bytes por campo, lo que permite almacenar todos los elementos de una matriz cuadrada de tamaño máximo `²√1500= ±38.7298334620742` en un único String.

### Archivo de credenciales de Google Cloud Platform (GCP)

El archivo de credenciales que se utiliza para autenticarse en GCP se debe dejar ubicado en: `/resources/firestorekeys.json`.

Para obtener el archivo JSON se deben seguir los pasos descritos en [Crear cuentas de servicio](https://support.google.com/a/answer/7378726?hl=es) en la sección **Paso 4: Crea la cuenta de servicio**.  

#### Notas
Durante el paso **Otorga a esta cuenta de servicio acceso al proyecto**, se debe seleccionar la función **Firebase Admin SDK Service Agent**.


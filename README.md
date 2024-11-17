# Anteproyecto: Focus Pro 

## 1. Título del Proyecto 
**Focus Pro**

## 2. Definición del Proyecto 
Focus Pro es una aplicación móvil nativa para Android (pensada para la migración a lo más actual que son las aplicaciones híbridas) diseñada para mejorar la productividad diaria de los usuarios a través de la gestión de tareas y objetivos personalizados. La aplicación permite a los usuarios establecer metas diarias y organizar sus tareas de manera efectiva. Como característica innovadora, Focus Pro incorpora inteligencia artificial (se intentará la implementación de OpenAI) para analizar las actividades del día y el progreso de las tareas completadas, proporcionando feedback personalizado al usuario al finalizar la jornada. 

El objetivo de Focus Pro es ayudar a las personas a ser más organizadas y productivas, brindándoles un control total sobre sus tareas y objetivos. Los usuarios podrán definir sus propias metas, como “concluir tres tareas diarias” o “reducir el tiempo de procrastinación”, y la IA proporcionará informes detallados y sugerencias para mejorar el enfoque y la eficiencia. 

## 3. Objetivos
- Controlar y organizar las tareas diarias del usuario 
- Definir y gestionar objetivos como “Concluir tres tareas diarias” o “reducir el tiempo de procrastinación” 
- Incorporar un temporizador Pomodoro*
- Análisis y feedback mediante inteligencia artificial (opcional) proporcionando informes y apartados a mejorar 
- Bloquear notificaciones y el acceso a las aplicaciones durante el período que se active el Pomodoro 
- Permitir la creación de recordatorios para eventos importantes como la toma de medicación, citas y más. 

### Temporizador Pomodoro*
Focus Pro incluirá un temporizador Pomodoro, que divide el tiempo de trabajo en intervalos para mejorar la concentración. Durante estos intervalos, se bloquearán notificaciones y aplicaciones seleccionadas por el usuario, reduciendo así las distracciones y mejorando la productividad. 

**Técnica Pomodoro:**
1. Elige una tarea en la que concentrarte. 
2. Configura un temporizador a 25 minutos y trabaja en la tarea hasta que suene el temporizador. 
3. Toma un descanso corto de 5 minutos. 
4. Repite el ciclo y, tras cuatro Pomodoros, toma un descanso más largo de 15 a 30 minutos. 

## 4. Asignaturas del Ciclo en las que se Apoyará el Proyecto 
- **Programación de Dispositivos Móviles (PMDM):** Uso de Jetpack Compose como técnica para el desarrollo de interfaces gráficas modernas y reactivas. Se aprovecharán también las capacidades de Firebase para gestionar la autenticación, bases de datos y el almacenamiento de archivos (como imágenes).
- **Acceso a Datos:** Implementación de la conexión con bases de datos locales (SQLite) y en la nube (Firebase Firestore) para la sincronización de datos. Además, se explorará la integración con APIs externas, como OpenAI para los análisis de productividad. 
- **Programación de Servicios y Procesos (PSP):** Desarrollar tareas que ejecuten procesos en segundo plano para el manejo de notificaciones, el temporizador y el análisis de datos mientras la aplicación no está en uso. 
- **Desarrollo de Interfaces:** Aplicar el patrón MVC (Model View Controller) para una correcta separación de la lógica, la gestión de datos y la presentación de la información al usuario. 

## 5. Tecnologías y Herramientas 
- **Lenguaje de Programación:** Kotlin. 
- **Framework:** Jetpack Compose para la creación de interfaces nativas reactivas. 
- **Base de Datos:** SQLite para almacenamiento local (optativo) y Firebase para sincronización en la nube. 
- **Inteligencia Artificial (optativo):** Uso de OpenAI para proporcionar feedback y recomendaciones personalizadas. 
- **IDE:** Android Studio. 
- **Control de Versiones:** GitHub. 
- **Librerías de Análisis Gráfico:** Librerías de Jetpack Compose para la visualización de datos en gráficos. 

## 6. Apartados a Implementar 
1. **Gestión de Tareas y Objetivos:** 
   - Crear, editar y eliminar tareas y objetivos. 
   - Establecer detalles como la prioridad, fecha límite y progreso. 
2. **Implementación de Temporizador Pomodoro:** 
   - Definición de intervalos de trabajo y descanso. 
   - Bloqueo de notificaciones y aplicaciones durante los intervalos de concentración. 
   - Posibilidad de adaptar los tiempos de concentración y descanso a gusto del usuario. 
3. **Feedback Personalizado mediante IA (Optativo):** 
   - Análisis del progreso diario y recomendaciones mediante algoritmos de IA. 
   - Visualización de gráficos con tendencias y rendimiento diario, semanal o mensual. 
4. **Gestión del Perfil del Usuario:** 
   - Crear un perfil de usuario para almacenar información como nombre, metas, progreso, etc. 
   - Permitir personalización de preferencias, objetivos y configuración de notificaciones. 
5. **Autenticación de Usuarios:** 
   - Implementar un sistema de autenticación (registro y login). 
   - Utilizar Firebase Authentication para un acceso seguro mediante correo electrónico y contraseña. (Optativo a implementar métodos de registro y login alternativos compatibles con Firebase). 
6. **Progreso y Análisis:** 
   - Llevar un registro detallado del progreso del usuario. 
   - Visualizar el rendimiento y la productividad mediante gráficos interactivos (del estilo de gráficas como anillos, más visuales y estéticos). 
7. **Sistema de Ranking:** 
   - Crear un sistema de ranking para comparar el progreso del usuario con sus objetivos personales o incluso con una comunidad de usuarios anónimos (optativo). 
8. **Aplicación Multilenguaje:** 
   - Implementar la aplicación en varios idiomas, principalmente español, inglés y gallego, pero abierta a futuras ampliaciones. 
   - Selección automática del idioma según la configuración del dispositivo o manualmente desde la configuración de la aplicación. 
9. **Notificaciones y Recordatorios Inteligentes:** 
   - Programación de recordatorios basados en objetivos no cumplidos o tareas pendientes. 
   - Envío de mensajes motivacionales y consejos basados en el desempeño del usuario. 
10. **Persistencia de Datos y Sincronización en la Nube:** 
    - Almacenamiento seguro de las tareas, metas y progreso en una base de datos local (SQLite). 
    - Sincronización opcional con Firebase para respaldo y acceso desde múltiples dispositivos. 

## 7. FUENTES: 
Método Pomodoro: [Wikipedia](https://es.wikipedia.org/wiki/T%C3%A9cnica_Pomodoro)  

**Derechos de Autor y Copyright**  
Copyright © 2024 David Cornado González. Todos los derechos reservados. Este documento y su contenido son propiedad exclusiva de David Cornado González. Está prohibida la reproducción total o parcial, distribución, comunicación pública o transformación de este documento sin el consentimiento previo por escrito del titular de los derechos. 

Focus Pro y todos los nombres comerciales, logotipos, contenidos, diseños y especificaciones mencionadas en este documento están protegidos por las leyes de propiedad intelectual e industrial. Se prohíbe expresamente el uso, copia o modificación no autorizada de cualquier parte de este documento o del proyecto descrito en él. Cualquier intento de plagio, uso indebido o distribución de este proyecto sin autorización puede resultar en acciones legales según las leyes vigentes en materia de propiedad intelectual y derechos de autor.
"# FOCUSPRO" 
"# FOCUSPRO" 
"# FOCUSPRO" 

package org.equiposeis.huellitasaventureras.dataModels

class UsuarioPaseador(
    var capacitacion: String = "",
    id_paseador: String = "",
    nombre:String = "",
    genero:Int = 0,
    edad:Int = 0,
    numero_telefonico:Long = 0L,
    domicilio:String = "",
    correo_electronico:String = "",
    tipo_usuario:Int
): Persona(id_paseador, nombre, genero, edad, numero_telefonico, domicilio, correo_electronico, tipo_usuario)
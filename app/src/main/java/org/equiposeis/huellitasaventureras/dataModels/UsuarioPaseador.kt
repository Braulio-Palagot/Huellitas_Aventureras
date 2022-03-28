package org.equiposeis.huellitasaventureras.dataModels

class UsuarioPaseador(
    var capacitacion: String = "",
    foto_perfil: String = "",
    id_paseador: String = "",
    nombre:String = "",
    genero:String = "",
    edad:Int = 0,
    numero_telefonico:Long = 0L,
    domicilio:String = "",
    correo_electronico:String = "",
    tipo_usuario:String = ""
): Persona(
    foto_perfil,
    id_paseador, nombre, genero, edad, numero_telefonico, domicilio, correo_electronico, tipo_usuario)
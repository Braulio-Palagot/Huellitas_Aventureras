package org.equiposeis.huellitasaventureras.dataModels

class UsuarioPaseador(
    var id_paseador: String = "",
    var capacitacion: String = "",
    var metodo_cobro: String = "",
    nombre:String = "",
    genero:Int = 0,
    edad:Int = 0,
    numero_telefonico:Long = 0L,
    domicilio:String = "",
    correo_electronico:String = "",
    tipo_usuario:Int
): Persona(nombre, genero, edad, numero_telefonico, domicilio, correo_electronico, tipo_usuario)
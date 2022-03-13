package org.equiposeis.huellitasaventureras.dataModels

open class Persona(
    var foto_perfil: String,
    var id_usuaio: String,
    var nombre: String,
    var genero: Int,
    var edad: Int,
    var numero_telefonico: Long,
    var domicilio: String,
    var correo_electronico: String,
    var tipo_usuario: Int,
    var metodo_pago: String = "",
    var Numer_pago: Int = 0,
    var CVV: Int = 0,
)

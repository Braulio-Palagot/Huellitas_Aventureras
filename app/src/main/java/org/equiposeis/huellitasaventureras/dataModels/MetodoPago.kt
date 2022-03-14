package org.equiposeis.huellitasaventureras.dataModels

data class MetodoPago(
        var ID_Usuario:String ="",
        var Titular:String ="",
        var Metodo_pago: String = "",
        var Numero_pago: String = "",
        var CVV: Int = 0,
)

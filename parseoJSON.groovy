import groovy.xml.StreamingMarkupBuilder

import groovy.xml.*
import groovy.json.*

def xmlSource = new File('payloadParseroCandidatoJSON.json')


def idOferta
def results = new JsonSlurper().parse(xmlSource)
def writer = new StringWriter()
def builder = new groovy.xml.MarkupBuilder(writer)
def helper = new groovy.xml.MarkupBuilderHelper(builder)
//def builder = new StreamingMarkupBuilder(writer)

helper.xmlDeclaration([version:'1.0', encoding:'UTF-8'])


builder.datosOfertas {


for (ofertas in results.mapeoProcesoFin.datosOfertas) {


 	if ( ofertas.EstadoRegistro == null && ofertas.IDUsuario == null && ofertas.HoraIniSegmento==null && ofertas.HoraFinSegmento==null ){ //indices ID Oferta

 			for (empleado in results.mapeoProcesoFin.datosOfertas) {


			 	if ( (empleado.EstadoRegistro != null && empleado.EstadoRegistro == 'ENROLL ' ) && empleado.IDOfertaProg == ofertas.IDOfertaProg ){ //empleados enrolados
			 		
			 		for (segmento in results.mapeoProcesoFin.datosOfertas) {
					 	
						if (  segmento.IDOfertaProg == ofertas.IDOfertaProg &&  ( segmento.HoraIniSegmento != null  && segmento.HoraFinSegmento != null ) ) { //segmentos de horario de la misma oferta
					 		builder.inscrito 
					 		{
					 			idOfertaProgramada(ofertas.IDOfertaProg)
					 			IDUsuarioInscrito(empleado.IDUsuario)
					 			FechaIniSegmento(segmento.FechaIniSegmento)
							 	FechaFinSegmento(segmento.FechaFinSegmento)
					 			HoraIniSegmento(segmento.HoraIniSegmento)
					 			HoraFinSegmento(segmento.HoraFinSegmento)
							}

						}

					}

			}
			
	}

}
}

}



def resultadoTXT = writer.toString()

//def xmlOut = new groovy.xml.XMLParser().parseText(resultadoTXT)

def root = new XmlParser().parseText(resultadoTXT);

def xml = XmlUtil.serialize(root);

println xml


def String horaMil(String horaIn){

	(ampm, hora) = horaIn.tokenize(' ');

	cadenaHora.tokenize( ':' )
	        switch (ampm) {
        case 'AM':
            
            break
        case 'PM':

            switch(hora) {
                case '01':
                    hora = '13'
                break
                case '02':
                    hora = '14'
                break
                case '03':
                    hora = '15'
                break
                case '04':
                    hora = '16'
                break
                case '05':
                    hora = '17'
                break
                case '06':
                    hora = '18'
                break
                case '07':
                    hora = '19'
                break
                case '08':
                    hora = '20'
                break
                case '09':
                    hora = '21'
                break
                case '10':
                    hora = '22'
                break
                case '11':
                    hora = '23'
                break
                case '12':
                    hora = '24'
                break

            }

            break
        }
        return hora
}

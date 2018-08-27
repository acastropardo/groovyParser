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

helper.xmlDeclaration([version:'1.0', encoding:'UTF-8', standalone:'no'])


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




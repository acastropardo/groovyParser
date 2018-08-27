def xmlSource = new File('payload.xml')
def bibliography = new XmlParser().parse(xmlSource)

println bibliography.'bib:author'.text()
bibliography.'lit:play'
 .findAll { it.'lit:year'
 .text().toInteger() > 1592 }
 .each { println it.'lit:title'.text() }
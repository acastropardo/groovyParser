def xmlSource = new File('payload.xml')
def bibliography = new XmlSlurper().parse(xmlSource)

bibliography.play
 .findAll { it.year.toInteger() > 1592 }
 .each { println it.title }
import groovy.xml.*
def writer = new StringWriter()
def xml = new MarkupBuilder(writer)

//List of items represented as a map
def items = [[itemCode: "A", unitPrice: 10, quantity: 2], 
             [itemCode: "B", unitPrice: 20, quantity: 3], 
             [itemCode: "C", unitPrice: 30, quantity: 4], 
             [itemCode: "D", unitPrice: 40, quantity: 6], 
             [itemCode: "E", unitPrice: 50, quantity: 5]]

xml.Order{
  StoreID("Store1")
  City("New York")
  Items{
    //Loop through the list.
    //make sure you are using a variable name instead of using "it"
    items.each{item->
      Item{
        ItemCode(item.itemCode)
        UnitPrice(item.unitPrice)
        Quantity(item.quantity)
      }
    }
  }
}

println writer
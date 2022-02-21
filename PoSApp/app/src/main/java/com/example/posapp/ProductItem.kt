package com.example.posapp

class ProductItem(ProductName:String,ProductPrice:Int,ProductID:Int):Product() {
    override val id: Int=ProductID
    override val name:String=ProductName
    override val price:Int=ProductPrice

    companion object{
        fun createMacaronList():ArrayList<Product>{
            val listofMacaron = ArrayList<Product>()
            listofMacaron.add(ProductItem("Black Macaron",15,1001))
            listofMacaron.add(ProductItem("Blue Macaron",13,1002))
            listofMacaron.add(ProductItem("Green Macaron",16,1003))
            listofMacaron.add(ProductItem("Navy Macaron",17,1004))
            listofMacaron.add(ProductItem("Pink Macaron",12,1005))
            listofMacaron.add(ProductItem("Red Macaron",11,1006))
            listofMacaron.add(ProductItem("Yellow Macaron",14,1007))
            return listofMacaron
        }

        fun createDrinkList():ArrayList<Product>{
            val drinkList = ArrayList<Product>()
            drinkList.add(ProductItem("Soft drinks",10,2001))
            drinkList.add(ProductItem("Water",7,2002))
            drinkList.add(ProductItem("Coffee",15,2003))
            drinkList.add(ProductItem("Liquor",40,2004))
            return drinkList
        }
        fun createDessertList():ArrayList<Product>{
            val dessertList = ArrayList<Product>()
            dessertList.add(ProductItem("Blueberry Cheese Cake",30,3001))
            dessertList.add(ProductItem("Strawberry Cheese Cake",29,3002))
            dessertList.add(ProductItem("Chocolate cake",31,3003))
            dessertList.add(ProductItem("Apple Pie",20,3004))
            return dessertList
        }

        fun createIceCreamList():ArrayList<Product>{
            val icecreamlist = ArrayList<Product>()
            icecreamlist.add(ProductItem("Vanilla",30,4001))
            icecreamlist.add(ProductItem("Chocolate",29,4002))
            icecreamlist.add(ProductItem("Egg nog",31,4003))
            icecreamlist.add(ProductItem("Milk",20,4004))
            return icecreamlist
        }

        fun createThaiDishList():ArrayList<Product>{
            val thaidishlist = ArrayList<Product>()
            thaidishlist.add(ProductItem("Pad KraPow",30,5001))
            thaidishlist.add(ProductItem("Fried Rice",29,5002))
            thaidishlist.add(ProductItem("Tom Yun Kung",31,5003))
            thaidishlist.add(ProductItem("Pad Thai",20,5004))
            return thaidishlist
        }

        fun createFruitList():ArrayList<Product>{
            val fruitlist = ArrayList<Product>()
            fruitlist.add(ProductItem("Pineapple",30,6001))
            fruitlist.add(ProductItem("Apple",29,6002))
            fruitlist.add(ProductItem("Orange",31,6003))
            fruitlist.add(ProductItem("Watermelon",20,6004))
            return fruitlist
        }

    }
}
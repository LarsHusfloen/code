class Cat extends Mammal{

  Cat(String navn, int alder){
    this.navn = navn;
    this.alder = alder;
  }

  public void mjau(){
    lyd("Mjau");
  }
}
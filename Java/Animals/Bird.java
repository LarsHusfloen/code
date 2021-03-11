class Bird extends Animals{
  private int egg;

  Bird(String navn, int alder, int egg){
    this.navn = navn;
    this.alder = alder;
    this.egg = egg;
  }

  public void syng(){
    System.out.println("Tralala");
  }
}
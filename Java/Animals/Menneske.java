class Human extends Mammal{
  private String jobb;

  Menneske(String jobb, String navn, int alder){
    this.navn = navn;
    this.alder = alder;
    this.jobb = jobb;
  }

  public void snakk(String setning){
    lyd(setning);
  }
}
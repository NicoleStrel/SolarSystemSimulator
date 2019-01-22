/** 
* [Clock.java]
*  @author Nicole Streltsov
*  Measures the elapsed  time of the system to be used in the main simulation frame
* January 2019
*/
class Clock {
    long elapsedTime = 0L;
    long lastTimeCheck = System.nanoTime();
    
    /** Clock *******************************************
	  * constructor for the  clock class
	  */
    public Clock() {
    }
    
    /** update *******************************************
      * updates the time  elapsed  when  called (gets current times from the system)
      */
    public void update() {
        long currentTime = System.nanoTime();
        this.elapsedTime = currentTime - this.lastTimeCheck;
        this.lastTimeCheck = currentTime;
    }
    
    /** getElapsedTime *******************************************
     * returns the elapsed time measured  by the clock
     * @return double elapsed time inseconds
     */
    public double getElapsedTime() {
        return (double)this.elapsedTime / 1.0E9D;
    }
}



class Clock {
    long elapsedTime = 0L;
    long lastTimeCheck = System.nanoTime();

    public Clock() {
    }

    public void update() {
        long currentTime = System.nanoTime();
        this.elapsedTime = currentTime - this.lastTimeCheck;
        this.lastTimeCheck = currentTime;
    }

    public double getElapsedTime() {
        return (double)this.elapsedTime / 1.0E9D;
    }
}



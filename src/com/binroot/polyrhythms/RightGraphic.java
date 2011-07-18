package com.binroot.polyrhythms;

import android.graphics.Bitmap;

public class RightGraphic {
    private Bitmap _bitmap;
    private Coordinates _coordinates;
    private boolean clicked;
    private RightGraphic prevGraphic;
 
    public RightGraphic(Bitmap bitmap, RightGraphic prevGraphic) {
        _bitmap = bitmap;
        _coordinates = new Coordinates();
        clicked = false;
        this.prevGraphic = prevGraphic;
    }
    
    public boolean getPrevGraphicState() {
    	if(prevGraphic!=null) {
    		return prevGraphic.getClicked();
    	}
    	else return true;
    }
    
    public void setClicked(boolean clicked) {
    	this.clicked = clicked;
    }
    public boolean getClicked() {
    	return clicked;
    }
    
    public Bitmap getGraphic() {
        return _bitmap;
    }
    
    public void setGraphic(Bitmap b) {
    	_bitmap = b;
    }
 
    public Coordinates getCoordinates() {
        return _coordinates;
    }
 
    /**
     * Contains the coordinates of the graphic.
     */
    public class Coordinates {
        private int _x = 100;
        private int _y = 0;
 
        public int getX() {
            return _x + _bitmap.getWidth() / 2;
        }
 
        public void setX(int value) {
            _x = value - _bitmap.getWidth() / 2;
        }
 
        public int getY() {
            return _y + _bitmap.getHeight() / 2;
        }
 
        public void setY(int value) {
            _y = value - _bitmap.getHeight() / 2;
        }
 
        public String toString() {
            return "Coordinates: (" + _x + "/" + _y + ")";
        }
    }

    
}
import java.util.HashMap;

/**
 * Seven segment display to display numbers 0-9.<br/>
 * @author SAl0nKA
 * @version 4.1.2022
 */
public class SSD {

 //      A
 //   F     B
 //      G
 //   E     C
 //      D

    private HashMap<String, Segment> segments;

    /** Constructor for SSD.
     * @param segmentLength Length of segment.
     * @param segmentWidht Widht of segment.
     * @param displayPosX
     * @param displayPosY */
    public SSD(int segmentLength, int segmentWidht, int displayPosX, int displayPosY) {
        this.segments = new HashMap<>();
        this.segments.put("a", new Segment(segmentLength, segmentWidht, displayPosX + segmentWidht, displayPosY));
        this.segments.put("b", new Segment(segmentWidht, segmentLength, displayPosX + segmentLength + segmentWidht, displayPosY + segmentWidht));
        this.segments.put("c", new Segment(segmentWidht, segmentLength, displayPosX + segmentLength + segmentWidht, displayPosY + 2 * segmentWidht + segmentLength));
        this.segments.put("d", new Segment(segmentLength, segmentWidht, displayPosX + segmentWidht, displayPosY + 2 * segmentWidht + 2 * segmentLength));
        this.segments.put("e", new Segment(segmentWidht, segmentLength, displayPosX, displayPosY + 2 * segmentWidht + segmentLength));
        this.segments.put("f", new Segment(segmentWidht, segmentLength, displayPosX, displayPosY + segmentWidht));
        this.segments.put("g", new Segment(segmentLength, segmentWidht, displayPosX + segmentWidht, displayPosY + segmentWidht + segmentLength));
    }

    /** Shows provided digit
     * @param digit Number from 0-9*/
    public void showDigit(int digit) {
        //prejde všetky kody kym nenajde dane čislo
        for (SegmentCode s:SegmentCode.values()) {
            if (s.value == digit) {
                this.lightDownAll();
                //podla danych kodov rozsvieti konkretne segmenty
                for (String segmentIndex: s.codes) {
                    this.segments.get(segmentIndex).lightUp();
                }
            }
        }
    }

    /** Lights down all segments.*/
    private void lightDownAll() {
        for (Segment s: this.segments.values()) {
            s.lightDown();
        }
    }

    /** Changes color of all segments
     * @param color New color.*/
    public void changeColor(String color) {
        for (Segment s:this.segments.values()) {
            s.changeColor(color);
        }
    }

    /** Enum that holds codes for displaying numbers using SSD*/
    private enum SegmentCode {
        EINS(1, "c", "b"),
        ZWEI(2, "a", "b", "d", "e", "g"),
        DREI(3, "a", "b", "c", "d", "g"),
        VIER(4, "b", "c", "f", "g"),
        FUNF(5, "a", "c", "d", "f", "g"),
        SECHS(6, "a", "c", "d", "e", "f", "g"),
        SIEBEN(7, "a", "b", "c"),
        ACHT(8, "a", "b", "c", "d", "e", "f", "g"),
        NEUN(9, "a", "b", "c", "d", "f", "g"),
        NULL(0, "a", "b", "c", "d", "e", "f");

        private int value;
        private String[] codes;

        /** Constructor for SegmentCode
         * @param value Number which the codes represent.
         * @param codes Codes for displaying provided number.*/
        SegmentCode(int value, String... codes) {
            this.value = value;
            this.codes = codes;
        }

        /** Retrun value of the represented number.*/
        public int getValue() {
            return this.value;
        }
    }
}

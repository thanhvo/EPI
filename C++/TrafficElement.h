#ifndef TRAFFIC_ELEMENT_H
#define TRAFFIC_ELEMENT_H

class TrafficElement {
    public:
        int time, volume;
        
        TrafficElement(int __time, int __volume) {
            time = __time;
            volume = __volume;
        }
        
        const bool operator<(const TrafficElement &that) const {
            return volume < that.volume;
        }
        
        const bool operator==(const TrafficElement &that) const {
            return time == that.time && volume == that.volume;
        }
                
};

#endif
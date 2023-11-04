#include "functions.h"


int main() {
    Track* track = new Track;
    newTrackFromUser(track);
    printTrack(track);
    return 0;
}

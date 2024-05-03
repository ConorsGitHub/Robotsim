Robot simulation <br />
<br />
Overview: <br />
Multiple robot classes, with different properties and behaviours interact with one another in the robot arena, drawn using the MyCanvas class.  <br />
Robots can be added, moved or removed by selecting robots and using the menu <br />
The simulation can be paused, resumed and ended using the menu features <br />
The state of the simulation can be saved and loaded using the menu features <br />
<br />
Robot properties: <br />
(mobile- robot can move, bouncing off of walls and other robots) <br />
(immobile- robot remains still)<br />
Blocker Robot- Immobile, Serves as an obstacle for other Robots, has no other properties<br />
Target Robot- Immobile, keeps score of the number of times it has collided with the Game Robot<br />
Game Robot- Mobile, is the only robot that can increment the score kept by the Target Robot during collision<br />
Evil Robot- Mobile, during collision with Game Robot, Game Robot is deleted<br />
Beam Robot- Mobile, Has beams facing the direction of movement, once another robot is within these beams the Beam Robot changes direction to avoid direct collision<br />
Whisker Robot- Mobile, has whiskers facing at +/-45 degrees of direction of movement, once another robot collides with these whiskers the Whisker Robot changes direction<br />
Bumper Robot- Mobile, surrounded by a 360 degree bumper, once the bumper collides with another Robot the Bumper Robot changes direction to avoid collision<br />
Slider Robot- Mobile, only moves laterally, serving as an obstacle for other Robots<br />

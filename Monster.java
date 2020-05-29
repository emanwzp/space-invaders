
public class Monster extends Actor {
		private boolean borderLimit = false;
		private boolean directionRight = true;
		
		public Monster(SpaceInvaders spaceInvaders) {
				
	}

		public void monsterMove() {
			if(x <= positionIncrement/2 ){//if this is true it means the monster is too much to the left so its movement needs to change	
				borderLimit = true;
				if(i < positionIncrement/2) {
					y = y + 1;
					i = i +1;
				}
				else {
					borderLimit = false;
					directionRight = true;
					x = x + 1;
					i = 0;
				}
			}
			else if(x >= Stage.WIDTH - positionIncrement ){ //if this is true it means the monster is too much to the right
				borderLimit = true;
				if(i < positionIncrement/2) {
					y = y + 1;
					i = i + 1;
				}
				else {
					borderLimit = false;
					directionRight =false;
					x = x - 1;
					i = 0;
				}
				
			}
			else if (directionRight==true && borderLimit == false){
				x = x + 1;
				//move left
			}else if(directionRight==false && borderLimit == false){
				x = x -1;
				//move right
			}
		}
			
		}		


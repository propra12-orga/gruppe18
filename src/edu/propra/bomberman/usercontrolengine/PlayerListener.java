package edu.propra.bomberman.usercontrolengine;

import java.awt.event.KeyEvent;
import java.util.HashMap;

import edu.propra.bomberman.gameengine.actions.ActionObject;
import edu.propra.bomberman.gameengine.actions.BombDownAction;
import edu.propra.bomberman.gameengine.actions.StartMoveAction;
import edu.propra.bomberman.gameengine.actions.StopMoveAction;
import edu.propra.bomberman.gameengine.objects.Player;

public class PlayerListener extends Listener {

	private boolean spacedown=false;
	
	public PlayerListener(Player player) {
		this.actor=player;
	}
	
	@Override
	public ActionObject keyDownEvent(HashMap<Integer, Integer> keysDown,Integer keyCode) {
		boolean up,left,down,right,upleft,upright,downleft,downright;
		down=keysDown.containsKey(KeyEvent.VK_UP );
		up=keysDown.containsKey(KeyEvent.VK_DOWN );
		left=keysDown.containsKey(KeyEvent.VK_LEFT );
		right=keysDown.containsKey(KeyEvent.VK_RIGHT);
		
		if(up && down){up=false;down=false;}
		if(left && right){left=false;right=false;}
		
		
		upleft=(up && left);
		upright=(up && right);
		downleft=(down && left);
		downright=(down && right);
		
		
		if(keyCode!=KeyEvent.VK_SPACE){
			if(upleft){
				return new StartMoveAction(315,this.actor,System.currentTimeMillis());
			}else if(upright){
				return new StartMoveAction(45,this.actor,System.currentTimeMillis());
			}else if(downleft){
				return new StartMoveAction(225,this.actor,System.currentTimeMillis());
			}else if(downright){
				return new StartMoveAction(135,this.actor,System.currentTimeMillis());
			}else if(down){
				return new StartMoveAction(180,this.actor,System.currentTimeMillis());
			}else if(up){
				return new StartMoveAction(0,this.actor,System.currentTimeMillis());
			}else if(right){
				return new StartMoveAction(90,this.actor,System.currentTimeMillis());
			}else if(left){
				return new StartMoveAction(270,this.actor,System.currentTimeMillis());
			}else{
				return new StopMoveAction(this.actor,System.currentTimeMillis());	
			}
		}else{
			if(!spacedown){
				spacedown=true;
				return new BombDownAction(this.actor,System.currentTimeMillis());
			}	
		}
		return null;
	}

	@Override
	public ActionObject keyUpEvent(HashMap<Integer, Integer> keysDown, Integer keyRelease) {
		boolean up,left,down,right, upleft,upright,downleft,downright;
		down=keysDown.containsKey(KeyEvent.VK_UP );
		up=keysDown.containsKey(KeyEvent.VK_DOWN );
		left=keysDown.containsKey(KeyEvent.VK_LEFT );
		right=keysDown.containsKey(KeyEvent.VK_RIGHT);
		
		if(up && down){up=false;down=false;}
		if(left && right){left=false;right=false;}
		
		upleft=(up && left);
		upright=(up && right);
		downleft=(down && left);
		downright=(down && right);
		
		
		if(keyRelease!=KeyEvent.VK_SPACE){
			if(upleft){
				return new StartMoveAction(315,this.actor,System.currentTimeMillis());
			}else if(upright){
				return new StartMoveAction(45,this.actor,System.currentTimeMillis());
			}else if(downleft){
				return new StartMoveAction(225,this.actor,System.currentTimeMillis());
			}else if(downright){
				return new StartMoveAction(135,this.actor,System.currentTimeMillis());
			}else if(down){
				return new StartMoveAction(180,this.actor,System.currentTimeMillis());
			}else if(up){
				return new StartMoveAction(0,this.actor,System.currentTimeMillis());
			}else if(right){
				return new StartMoveAction(90,this.actor,System.currentTimeMillis());
			}else if(left){
				return new StartMoveAction(270,this.actor,System.currentTimeMillis());
			}else{
				return new StopMoveAction(this.actor,System.currentTimeMillis());	
			}
		}else{
			spacedown=false;	
		}
		return null;
	}

	@Override
	public ActionObject keyTypedEvent(Integer keyCode) {
		if(keyCode==KeyEvent.VK_SPACE && !spacedown){
			return new BombDownAction(this.actor,System.currentTimeMillis());
		}
		return null;
	}

}

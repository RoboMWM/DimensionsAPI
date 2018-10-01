package me.xxastaspastaxx.dimensions.portal;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import me.xxastaspastaxx.dimensions.Main;
import me.xxastaspastaxx.dimensions.api.CPortal;
import me.xxastaspastaxx.dimensions.api.Dimensions;

public class FrameClass {
	//SUGGESTION
	//Instead of making the glass air for the player you should make the glass air
	//and make it look like glass for every player exept the ones that are in the same portal
	//idk if it would be better or not
	
	
	public static HashMap<Player, Location> plo = new HashMap<Player, Location>();
	
	//Hide glass pane when player is inside portal
	@SuppressWarnings("deprecation")
	public static void hide(Player p, CPortal cportal) {
		if (Dimensions.isInPortal(p, cportal)) {
			int rad = Main.getInstance().portalClass.rad;
			
			HashMap<CPortal, Integer> up = Main.getInstance().portalClass.up;
			HashMap<CPortal, Integer> down = Main.getInstance().portalClass.down;
			
			//Hide glass for portal with sides in East and West
			if (Main.getInstance().portalClass.hasGlassEW(cportal)) {
				HashMap<CPortal, Integer>east = Main.getInstance().portalClass.east;
				HashMap<CPortal, Integer> west = Main.getInstance().portalClass.west;
				  Location ploc = cportal.getLocation();
				  
				  if (true) {
					    Location location = ploc.clone();
					    for (int blocks = 1; blocks <= rad; blocks++) {
					        location.add(1, 0, 0);
					        if (location.getBlock().getType().equals(Material.STAINED_GLASS_PANE) || location.getBlock().getType().equals(Material.AIR)) {
					        	p.sendBlockChange(location, Material.AIR, (byte) 0);
						        }else {
						        	if ((location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) || location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)) {
						        	} else {
						        		if (cportal.getData()==-1) {
						        			if (location.getBlock().getType() == cportal.getMaterial()) {
						        			} else {break;}
						        		} else {break;}
						        	}
						        }
					        if (up.get(cportal) == 1) {
					        	if (location.getY() == ploc.getY()+1) {break;}
					        } else {
					        	if (location.getY() == ploc.getY()+up.get(cportal)) {break;}
					        }
					        if (location.getBlock().getType() == cportal.getMaterial()) {
					        	location.add(-(east.get(cportal)+1), 0, 0);
					        	location.add(0, 1, 0);
					        }
					    }
				  }
				  if (true) {
					    Location location = ploc.clone();
					    for (int blocks = 1; blocks <= rad; blocks++) {
					        location.add(-1, 0, 0);
					        if (location.getBlock().getType().equals(Material.STAINED_GLASS_PANE) || location.getBlock().getType().equals(Material.AIR)) {
					        	p.sendBlockChange(location, Material.AIR, (byte) 0);
						        }else {
						        	if ((location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) || location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)) {
						        	} else {
						        		if (cportal.getData()==-1) {
						        			if (location.getBlock().getType() == cportal.getMaterial()) {
						        			} else {break;}
						        		} else {break;}
						        	}
						        }
					        if (up.get(cportal) == 1) {
					        	if (location.getY() == ploc.getY()+1) {break;}
					        } else {
					        	if (location.getY() == ploc.getY()+up.get(cportal)) {break;}
					        }
					        if (location.getBlock().getType() == cportal.getMaterial()) {
					        	location.add(-(west.get(cportal)-1), 0, 0);
					        	location.add(0, 1, 0);
					        }
					    }
				  }
				  if (true) {
					    Location location = ploc.clone();
					    for (int blocks = 1; blocks <= rad; blocks++) {
					        location.add(1, 0, 0);
					        if (location.getBlock().getType().equals(Material.STAINED_GLASS_PANE) || location.getBlock().getType().equals(Material.AIR)) {
					        	p.sendBlockChange(location, Material.AIR, (byte) 0);
						        }else {
						        	if ((location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) || location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)) {	
						        	} else {
						        		if (cportal.getData()==-1) {
						        			if (location.getBlock().getType() == cportal.getMaterial()) {
						        			} else {break;}
						        		} else {break;}
						        	}
						        }
					        if (down.get(cportal) == 1) {
					        	if (location.getY() == ploc.getY()+1) {break;}
					        } else {
					        	if (location.getY() == ploc.getY()+down.get(cportal)) {break;}
					        }
					        if (location.getBlock().getType() == cportal.getMaterial()) {
					        	location.add(-(east.get(cportal)+1), 0, 0);
					        	location.add(0, -1, 0);
					        }
					    }
				  }
				  if (true) {
					    Location location = ploc.clone();
					    for (int blocks = 1; blocks <= rad; blocks++) {
					        location.add(-1, 0, 0);
					        if (location.getBlock().getType().equals(Material.STAINED_GLASS_PANE) || location.getBlock().getType().equals(Material.AIR)) {
					        	p.sendBlockChange(location, Material.AIR, (byte) 0);
						        }else {
						        	if ((location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) || location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)) {
						        	} else {
						        		if (cportal.getData()==-1) {
						        			if (location.getBlock().getType() == cportal.getMaterial()) {	
						        			} else {break;}
						        		} else {break;}
						        	}
						        }
					        if (down.get(cportal) == 1) {
					        	if (location.getY() == ploc.getY()+1) {break;}
					        } else {
					        	if (location.getY() == ploc.getY()+down.get(cportal)) {break;}
					        }
					        if (location.getBlock().getType() == cportal.getMaterial()) {
					        	location.add(-(west.get(cportal)-1), 0, 0);
					        	location.add(0, -1, 0);
					        }
					    }
				  }
			} else if (Main.getInstance().portalClass.hasGlassNS(cportal)) {//Hide glass for portal with sides in North and South
				  Location ploc = cportal.getLocation();
				  
				  HashMap<CPortal, Integer> north = Main.getInstance().portalClass.north;
					HashMap<CPortal, Integer> south = Main.getInstance().portalClass.south;
				  
				  if (true) {
					    Location location = ploc.clone();
					    for (int blocks = 1; blocks <= rad; blocks++) {
					        location.add(0, 0, 1);
					        if (location.getBlock().getType().equals(Material.STAINED_GLASS_PANE) || location.getBlock().getType().equals(Material.AIR)) {
					        	p.sendBlockChange(location, Material.AIR, (byte) 0);
						        }else {
						        	if ((location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) || location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)) {
						        	} else {
						        		if (cportal.getData()==-1) {
						        			if (location.getBlock().getType() == cportal.getMaterial()) {
						        			} else {break;}
						        		} else {break;}
						        	}
						        }
					        if (up.get(cportal) == 1) {
					        	if (location.getY() == ploc.getY()+1) {break;}
					        } else {
					        	if (location.getY() == ploc.getY()+up.get(cportal)) {break;}
					        }
					        if (location.getBlock().getType() == cportal.getMaterial()) {
					        	location.add(0, 0, -(south.get(cportal)+1));
					        	location.add(0, 1, 0);
					        }
					    }
				  }
				if (true) {
				    Location location = ploc.clone();
				    for (int blocks = 1; blocks <= rad; blocks++) {
				        location.add(0, 0, -1);
				        if (location.getBlock().getType().equals(Material.STAINED_GLASS_PANE) || location.getBlock().getType().equals(Material.AIR)) {
				        	p.sendBlockChange(location, Material.AIR, (byte) 0);
					        }else {
					        	if ((location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) || location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)) {
					        	} else {
					        		if (cportal.getData()==-1) {
					        			if (location.getBlock().getType() == cportal.getMaterial()) {
					        			} else {break;}
					        		} else {break;}
					        	}
					        }
				        if (up.get(cportal) == 1) {
				        	if (location.getY() == ploc.getY()+1) {break;}
				        } else {
				        	if (location.getY() == ploc.getY()+up.get(cportal)) {break;}
				        }
				        if (location.getBlock().getType() == cportal.getMaterial()) {
				        	location.add(0, 0, -(north.get(cportal)-1));
				        	location.add(0, 1, 0);
				        }
				    }
				}
				if (true) {
				    Location location = ploc.clone();
				    for (int blocks = 1; blocks <= rad; blocks++) {
				        location.add(0, 0, 1);
				        if (location.getBlock().getType().equals(Material.STAINED_GLASS_PANE) || location.getBlock().getType().equals(Material.AIR)) {
				        	p.sendBlockChange(location, Material.AIR, (byte) 0);
					        }else {
					        	if ((location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) || location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)) {
					        	} else {
					        		if (cportal.getData()==-1) {
					        			if (location.getBlock().getType() == cportal.getMaterial()) {
					        			} else {break;}
					        		} else {break;}
					        	}
					        }
				        if (down.get(cportal) == 1) {
				        	if (location.getY() == ploc.getY()+1) {break;}
				        } else {
				        	if (location.getY() == ploc.getY()+down.get(cportal)) {break;}
				        }
				        if (location.getBlock().getType() == cportal.getMaterial()) {
				        	location.add(0, 0, -(south.get(cportal)+1));
				        	location.add(0, -1, 0);
				        }
				    }
				}
				  if (true) {
					    Location location = ploc.clone();
					    for (int blocks = 1; blocks <= rad; blocks++) {
					        location.add(0, 0, -1);
					        if (location.getBlock().getType().equals(Material.STAINED_GLASS_PANE) || location.getBlock().getType().equals(Material.AIR)) {
					        	p.sendBlockChange(location, Material.AIR, (byte) 0);
						        }else {
						        	if ((location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) || location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)) {
						        	} else {
						        		if (cportal.getData()==-1) {
						        			if (location.getBlock().getType() == cportal.getMaterial()) {	
						        			} else {break;}
						        		} else {break;}
						        	}
						        }
					        if (down.get(cportal) == 1) {
					        	if (location.getY() == ploc.getY()+1) {break;}
					        } else {
					        	if (location.getY() == ploc.getY()+down.get(cportal)) {break;}
					        }
					        if (location.getBlock().getType() == cportal.getMaterial()) {
					        	location.add(0, 0, -(north.get(cportal)-1));
					        	location.add(0, -1, 0);
					        }
					    }
				  }
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void show(Player p, CPortal cportal) {
		if (Dimensions.isInPortal(p, cportal)) {
			int rad = Main.getInstance().portalClass.rad;
			
			HashMap<CPortal, Integer> up = Main.getInstance().portalClass.up;
			HashMap<CPortal, Integer> down = Main.getInstance().portalClass.down;
			
			//Show glass for portal with sides in East and West
			if (Main.getInstance().portalClass.hasGlassEW(cportal)) {
				  Location ploc = cportal.getLocation();
				  
					HashMap<CPortal, Integer>east = Main.getInstance().portalClass.east;
					HashMap<CPortal, Integer> west = Main.getInstance().portalClass.west;
				  
				  if (true) {
					    Location location = ploc.clone();
					    for (int blocks = 1; blocks <= rad; blocks++) {
					        location.add(1, 0, 0);
					        if (location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)) {
					        	p.sendBlockChange(location, Material.STAINED_GLASS_PANE, (byte) cportal.getGlassData());
						        }else {
						        	if ((location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) || location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)) {
						        	} else {
						        		if (cportal.getData()==-1) {
						        			if (location.getBlock().getType() == cportal.getMaterial()) {
						        			} else {break;}
						        		} else {break;}
						        	}
						        }
					        if (up.get(cportal) == 1) {
					        	if (location.getY() == ploc.getY()+1) {break;}
					        } else {
					        	if (location.getY() == ploc.getY()+up.get(cportal)) {break;}
					        }
					        if (location.getBlock().getType() == cportal.getMaterial()) {
					        	location.add(-(east.get(cportal)+1), 0, 0);
					        	location.add(0, 1, 0);
					        }
					    }
				  }
				  if (true) {
					    Location location = ploc.clone();
					    for (int blocks = 1; blocks <= rad; blocks++) {
					        location.add(-1, 0, 0);
					        if (location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)) {
					        	p.sendBlockChange(location, Material.STAINED_GLASS_PANE, (byte) cportal.getGlassData());
						        }else {
						        	if ((location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) || location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)) {
						        	} else {
						        		if (cportal.getData()==-1) {
						        			if (location.getBlock().getType() == cportal.getMaterial()) {
						        			} else {break;}
						        		} else {break;}
						        	}
						        }
					        if (up.get(cportal) == 1) {
					        	if (location.getY() == ploc.getY()+1) {break;}
					        } else {
					        	if (location.getY() == ploc.getY()+up.get(cportal)) {break;}
					        }
					        if (location.getBlock().getType() == cportal.getMaterial()) {
					        	location.add(-(west.get(cportal)-1), 0, 0);
					        	location.add(0, 1, 0);
					        }
					    }
				  }
				  if (true) {
					    Location location = ploc.clone();
					    for (int blocks = 1; blocks <= rad; blocks++) {
					        location.add(1, 0, 0);
					        if (location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)) {
					        	p.sendBlockChange(location, Material.STAINED_GLASS_PANE, (byte) cportal.getGlassData());
						        }else {
						        	if ((location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) || location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)) {	
						        	} else {
						        		if (cportal.getData()==-1) {
						        			if (location.getBlock().getType() == cportal.getMaterial()) {
						        			} else {break;}
						        		} else {break;}
						        	}
						        }
					        if (down.get(cportal) == 1) {
					        	if (location.getY() == ploc.getY()+1) {break;}
					        } else {
					        	if (location.getY() == ploc.getY()+down.get(cportal)) {break;}
					        }
					        if (location.getBlock().getType() == cportal.getMaterial()) {
					        	location.add(-(east.get(cportal)+1), 0, 0);
					        	location.add(0, -1, 0);
					        }
					    }
				  }
				  if (true) {
					    Location location = ploc.clone();
					    for (int blocks = 1; blocks <= rad; blocks++) {
					        location.add(-1, 0, 0);
					        if (location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)) {
					        	p.sendBlockChange(location, Material.STAINED_GLASS_PANE, (byte) cportal.getGlassData());
						        }else {
						        	if ((location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) || location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)) {
						        	} else {
						        		if (cportal.getData()==-1) {
						        			if (location.getBlock().getType() == cportal.getMaterial()) {	
						        			} else {break;}
						        		} else {break;}
						        	}
						        }
					        if (down.get(cportal) == 1) {
					        	if (location.getY() == ploc.getY()+1) {break;}
					        } else {
					        	if (location.getY() == ploc.getY()+down.get(cportal)) {break;}
					        }
					        if (location.getBlock().getType() == cportal.getMaterial()) {
					        	location.add(-(west.get(cportal)-1), 0, 0);
					        	location.add(0, -1, 0);
					        }
					    }
				  }
			} else if (Main.getInstance().portalClass.hasGlassNS(cportal)) {//Show glass for portal with sides in North and South
				  Location ploc = cportal.getLocation();
				  
				  HashMap<CPortal, Integer> north = Main.getInstance().portalClass.north;
					HashMap<CPortal, Integer> south = Main.getInstance().portalClass.south;
				  
				  if (true) {
					    Location location = ploc.clone();
					    for (int blocks = 1; blocks <= rad; blocks++) {
					        location.add(0, 0, 1);
					        if (location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)) {
					        	p.sendBlockChange(location, Material.STAINED_GLASS_PANE, (byte) cportal.getGlassData());
						        }else {
						        	if ((location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) || location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)) {
						        	} else {
						        		if (cportal.getData()==-1) {
						        			if (location.getBlock().getType() == cportal.getMaterial()) {
						        			} else {break;}
						        		} else {break;}
						        	}
						        }
					        if (up.get(cportal) == 1) {
					        	if (location.getY() == ploc.getY()+1) {break;}
					        } else {
					        	if (location.getY() == ploc.getY()+up.get(cportal)) {break;}
					        }
					        if (location.getBlock().getType() == cportal.getMaterial()) {
					        	location.add(0, 0, -(south.get(cportal)+1));
					        	location.add(0, 1, 0);
					        }
					    }
				  }
				if (true) {
				    Location location = ploc.clone();
				    for (int blocks = 1; blocks <= rad; blocks++) {
				        location.add(0, 0, -1);
				        if (location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)) {
				        	p.sendBlockChange(location, Material.STAINED_GLASS_PANE, (byte) cportal.getGlassData());
					        }else {
					        	if ((location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) || location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)) {
					        	} else {
					        		if (cportal.getData()==-1) {
					        			if (location.getBlock().getType() == cportal.getMaterial()) {
					        			} else {break;}
					        		} else {break;}
					        	}
					        }
				        if (up.get(cportal) == 1) {
				        	if (location.getY() == ploc.getY()+1) {break;}
				        } else {
				        	if (location.getY() == ploc.getY()+up.get(cportal)) {break;}
				        }
				        if (location.getBlock().getType() == cportal.getMaterial()) {
				        	location.add(0, 0, -(north.get(cportal)-1));
				        	location.add(0, 1, 0);
				        }
				    }
				}
				if (true) {
				    Location location = ploc.clone();
				    for (int blocks = 1; blocks <= rad; blocks++) {
				        location.add(0, 0, 1);
				        if (location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)) {
				        	p.sendBlockChange(location, Material.STAINED_GLASS_PANE, (byte) cportal.getGlassData());
					        }else {
					        	if ((location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) || location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)) {
					        	} else {
					        		if (cportal.getData()==-1) {
					        			if (location.getBlock().getType() == cportal.getMaterial()) {
					        			} else {break;}
					        		} else {break;}
					        	}
					        }
				        if (down.get(cportal) == 1) {
				        	if (location.getY() == ploc.getY()+1) {break;}
				        } else {
				        	if (location.getY() == ploc.getY()+down.get(cportal)) {break;}
				        }
				        if (location.getBlock().getType() == cportal.getMaterial()) {
				        	location.add(0, 0, -(south.get(cportal)+1));
				        	location.add(0, -1, 0);
				        }
				    }
				}
				  if (true) {
					    Location location = ploc.clone();
					    for (int blocks = 1; blocks <= rad; blocks++) {
					        location.add(0, 0, -1);
					        if (location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)) {
					        	p.sendBlockChange(location, Material.STAINED_GLASS_PANE, (byte) cportal.getGlassData());
						        }else {
						        	if ((location.getBlock().getType() == cportal.getMaterial() && location.getBlock().getState().getRawData() == cportal.getData()) || location.getBlock().getType().equals(Material.STAINED_GLASS_PANE)) {
						        	} else {
						        		if (cportal.getData()==-1) {
						        			if (location.getBlock().getType() == cportal.getMaterial()) {	
						        			} else {break;}
						        		} else {break;}
						        	}
						        }
					        if (down.get(cportal) == 1) {
					        	if (location.getY() == ploc.getY()+1) {break;}
					        } else {
					        	if (location.getY() == ploc.getY()+down.get(cportal)) {break;}
					        }
					        if (location.getBlock().getType() == cportal.getMaterial()) {
					        	location.add(0, 0, -(north.get(cportal)-1));
					        	location.add(0, -1, 0);
					        }
					    }
				  }
			}
		}
	}
	
}

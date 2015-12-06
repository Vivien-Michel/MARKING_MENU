package tp.mm.test;

import java.awt.Component;

public class Action {
	private Component component;
	private String name;
	private Action[] actions={};
	
	public Action(Component t,String name){
		this.component=t;
		this.name=name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Component getComponent() {
		return component;
	}

	public Action[] getActions() {
		return actions;
	}

	public void setActions(Action[] actions) {
		this.actions = actions;
	}

	public void action(){
	}

}

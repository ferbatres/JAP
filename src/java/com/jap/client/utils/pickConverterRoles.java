/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jap.client.utils;

import com.jap.client.dto.SRol;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

/**
 *
 * @author irvin_monterroza
 */
@FacesConverter("rolesPickListConverter")
public class pickConverterRoles implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return getObjectFromUIPickListComponent(component,value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object object) {
        String string;
		
		if(object == null){
			string="";
		}else{
			try{
				string = String.valueOf(((SRol)object).getId());
			}catch(ClassCastException cce){
				throw new ConverterException();
			}
		}
		return string;
    }
    
    
	private SRol getObjectFromUIPickListComponent(UIComponent component, String value) {
		final DualListModel<SRol> dualList;
		try{
			dualList = (DualListModel<SRol>) ((PickList)component).getValue();
			SRol team = getObjectFromList(dualList.getSource(),(value));
			if(team==null){
				team = getObjectFromList(dualList.getTarget(),(value));
			}
			
			return team;
		}catch(ClassCastException cce){
			throw new ConverterException();
		}catch(NumberFormatException nfe){
			throw new ConverterException();
		}
	}
        
        
        private SRol getObjectFromList(final List<?> list, final String identifier) {
		for(final Object object:list){
			final SRol team = (SRol) object;
			if(team.getId().equals(identifier)){
				return team;
			}
		}
		return null;
	}
    
}

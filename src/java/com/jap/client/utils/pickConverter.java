/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jap.client.utils;

import com.jap.client.dto.SOpcion;
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
@FacesConverter("opcionesPickListConverter")
public class pickConverter implements Converter{

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
				string = String.valueOf(((SOpcion)object).getId());
			}catch(ClassCastException cce){
				throw new ConverterException();
			}
		}
		return string;
    }
    
    
	private SOpcion getObjectFromUIPickListComponent(UIComponent component, String value) {
		final DualListModel<SOpcion> dualList;
		try{
			dualList = (DualListModel<SOpcion>) ((PickList)component).getValue();
			SOpcion team = getObjectFromList(dualList.getSource(),(value));
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
        
        
        private SOpcion getObjectFromList(final List<?> list, final String identifier) {
		for(final Object object:list){
			final SOpcion team = (SOpcion) object;
			if(team.getId().equals(identifier)){
				return team;
			}
		}
		return null;
	}
    
}

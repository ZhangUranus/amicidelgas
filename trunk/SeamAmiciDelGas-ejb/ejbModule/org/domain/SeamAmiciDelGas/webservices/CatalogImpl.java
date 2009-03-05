package org.domain.SeamAmiciDelGas.webservices;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.axis2.AxisFault;
import org.domain.SeamAmiciDelGas.entity.Cybercontadino;
import org.domain.SeamAmiciDelGas.webservices.CatalogMatchMakerStub.GetCategories;
import org.domain.SeamAmiciDelGas.webservices.CatalogMatchMakerStub.GetCategoriesResponse;
import org.domain.SeamAmiciDelGas.webservices.CatalogMatchMakerStub.GetDescription;
import org.domain.SeamAmiciDelGas.webservices.CatalogMatchMakerStub.GetDescriptionResponse;
import org.domain.SeamAmiciDelGas.webservices.CatalogMatchMakerStub.GetItems;
import org.domain.SeamAmiciDelGas.webservices.CatalogMatchMakerStub.GetItemsForCategory;
import org.domain.SeamAmiciDelGas.webservices.CatalogMatchMakerStub.GetItemsForCategoryResponse;
import org.domain.SeamAmiciDelGas.webservices.CatalogMatchMakerStub.GetItemsResponse;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;

@Name("catalogService")
@Scope(ScopeType.SESSION)
public class CatalogImpl implements CatalogInterface {

	
	@Out(value="categories") private List<String> categories;
	@Out(value="items") private List<Item> items;
	@Out(value="itemsForCategory") private List<Item> itemsForCategory;
	private Cybercontadino contadino;
	private String category;
	private CatalogMatchMakerStub cs;
	public CatalogImpl(){
		try {
			cs= new CatalogMatchMakerStub();
			categories= new ArrayList<String>();
			items= new ArrayList<Item>();
			itemsForCategory= new ArrayList<Item>();
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String[] getCategories(String idContadino) {
		categories= new ArrayList<String>();
		GetCategories catArgs= new GetCategories();
		catArgs.setIdContadino(idContadino);
		try {
			GetCategoriesResponse catResp = cs.getCategories(catArgs);
			String[]catArray=catResp.get_return();
			for(String category:catArray)
			categories.add(category);
			return catArray;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}

	public String getDescription(String idContadino) {
		GetDescription descArgs= new GetDescription();
		descArgs.setIdContadino(idContadino);
		try {
			GetDescriptionResponse descResp = cs.getDescription(descArgs);
			return descResp.get_return();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return null;
	}

	public Item[] getItems(String idContadino) {
		items= new ArrayList<Item>();
		GetItems itemArgs= new GetItems();
		itemArgs.setIdContadino(idContadino);
		try {
			GetItemsResponse itemResp=cs.getItems(itemArgs);
			CatalogMatchMakerStub.Item[] itemArray=itemResp.get_return();
			ItemImpl[] myItemArray= new ItemImpl[itemArray.length];
			int i=0;
			for(CatalogMatchMakerStub.Item item: itemArray){
				myItemArray[i]=new ItemImpl();
				myItemArray[i].setCategory(item.getCategory());
				myItemArray[i].setDescription(item.getDescription());
				myItemArray[i].setId(item.getId());
				myItemArray[i].setName(item.getName());
				myItemArray[i].setPrezzo(item.getPrezzo());
				items.add(myItemArray[i++]);
			}
			return myItemArray;
				
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Item[] getItemsForCategory(String idContadino, String category) {
		itemsForCategory= new ArrayList<Item>();
		GetItemsForCategory itemForCatArgs= new GetItemsForCategory();
		itemForCatArgs.setIdContadino(idContadino);
		itemForCatArgs.setCategory(category);
		
		try {
			GetItemsForCategoryResponse itemResp=cs.getItemsForCategory(itemForCatArgs);
			CatalogMatchMakerStub.Item[] itemArray=itemResp.get_return();
			ItemImpl[] myItemArray= new ItemImpl[itemArray.length];
			int i=0;
			for(CatalogMatchMakerStub.Item item: itemArray){
				myItemArray[i]=new ItemImpl();
				myItemArray[i].setCategory(item.getCategory());
				myItemArray[i].setDescription(item.getDescription());
				myItemArray[i].setId(item.getId());
				myItemArray[i].setName(item.getName());
				myItemArray[i].setPrezzo(item.getPrezzo());
				itemsForCategory.add(myItemArray[i++]);
			}
			return myItemArray;
				
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Cybercontadino getContadino() {
		return contadino;
	}

	public void setContadino(Cybercontadino contadino) {
		this.contadino = contadino;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}

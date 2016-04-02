package com.ske.minitrello.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class CardList implements Serializable {

    private String name;
    private List<Card> cards;



    private int id;

    public enum DBColumn{
        name("_name");

        private String colName;
        private DBColumn(String col){
            this.colName = col;
        }

        public String toString(){
            return colName;
        }

    }

    public CardList(String name) {
        this.name = name;
        cards = new ArrayList<Card>();
        id = -1;
    }

    public CardList(String name, List<Card> cards) {
        this.name = name;
        this.cards = cards;
        id = -1;
    }

    public CardList(String name, int id){
        this.name = name;
        this.id = id;
        cards = new ArrayList<Card>();

    }

    public CardList(String name, int id, List<Card> cards){
        this.name = name;
        this.id = id;
        this.cards = cards;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    public String getName() {
        return name;
    }

    public List<Card> getCards() {
        return cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardList cardList = (CardList) o;

        return name.equals(cardList.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

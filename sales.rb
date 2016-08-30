class Good
  def initialize(name, category, is_imported, price)
    @name = name
    @category = category
    @price = price
    @is_imported = is_imported
    @exemptions = ["books", "food", "medical"]
  end

  def calculate_sales_tax
    if @exemptions.include?(@category)
      sales_tax = 0
    else
      sales_tax = price * 0.1
    end
    sales_tax
  end

  def calculate_import_tax
    if @is_imported
      import_tax = price * 0.05
    else
      import_tax = 0
    end
    import_tax
  end

  def calculate_taxes
    calculate_sales_tax + calculate_import_tax
  end
end


while input = gets.chomp

end

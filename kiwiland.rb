  class Stack
  def initialize
    @elements = []
  end

  def push(el)
    @elements.push(el)
  end

  def pop
    @elements.pop
  end

  def empty?
    @elements.empty?
  end
end


class Town
  attr_reader :name
  attr_accessor :roads
  def initialize(name)
    @name = name
    @roads = []
  end
end

class Adjacency
  attr_reader :target, :weight
  def initialize(target, weight)
    @target = target
    @weight = weight
  end
end

adjacency_matrix = {}

adjacencies = gets.chomp.split(", ")
adjacencies.each do |adjacency|
  src, tgt, wght = adjacency.split("")
  if adjacency_matrix[src].nil?
    adjacency_matrix[src] = Town.new(src)
  end
  if adjacency_matrix[tgt].nil?
    adjacency_matrix[tgt] = Town.new(tgt)
  end

  adjacency_matrix[src].roads << Adjacency.new(tgt, wght.to_i)
end

# puts adjacency_matrix.inspect

class Router
  def initialize(adjacency_matrix)
    @matrix = adjacency_matrix
  end

  def get_distance_of_route(town_names)
    tot = 0
    town_names.each_with_index do |town_name, index|
      break if index == town_names.length - 1
      current_town = @matrix[town_name]
      next_town = town_names[index + 1]
      if adj = current_town.roads.find{|adj| adj.target == next_town}
        tot += adj.weight
      else
        return "NO SUCH ROUTE"
      end
    end
    tot
  end

  def get_number_of_trips(start_town, end_town, min_stops, max_stops)
    stack = Stack.new

    stack.push([start_town, 0])
    total_paths = 0

    until stack.empty?
      town, num_stops = stack.pop
      if town == end_town && num_stops >= min_stops
        total_paths += 1
      end
      if num_stops < max_stops
        @matrix[town].roads.each do |adj|
          stack.push([adj.target, num_stops + 1])
        end
      end
    end
    total_paths
  end

  def get_number_of_trips_distance(start_town, end_town, max_distance)
    stack = Stack.new

    stack.push([start_town, 0])
    total_paths = 0

    until stack.empty?
      town, distance = stack.pop
      if town == end_town && distance <= max_distance && distance > 0
        total_paths += 1
      end
      if distance < max_distance
        @matrix[town].roads.each do |adj|
          stack.push([adj.target, distance + adj.weight])
        end
      end
    end
    total_paths
  end

  def get_shortest_route_stack(start_town, end_town)

    # this assumes there is actually a valid route; I'd need to tweak it if the target is not actually reachable from the source
    stack = Stack.new

    stack.push([start_town, 0])
    shortest_dist = Float::INFINITY

    until stack.empty?
      town, distance = stack.pop
      if town == end_town && distance < shortest_dist && distance > 0
        shortest_dist = distance
      end
      if distance < shortest_dist
        @matrix[town].roads.each do |adj|
          stack.push([adj.target, distance + adj.weight])
        end
      end
    end
    shortest_dist
  end




  def get_shortest_route(start_town, end_town)
    distances = {}
    @matrix.keys.each do |key|
      distances[key] = Float::INFINITY
    end
    distances[start_town] = 0
    visited = []
    current = start_town

    # this assumes there is actually a shortest route; I'd need to tweak it if the target is not actually reachable from the source
    until current == end_town && distances[current] > 0
      visited << current
      @matrix[current].roads.each do |adj|
        proposed_distance = distances[current] + adj.weight
        if adj.target == start_town && start_town == end_town
          return proposed_distance
        else
          distances[adj.target] = [distances[adj.target], proposed_distance].min
        end
      end
      current = distances.reject{|key, value| visited.include? key}.min_by{|key, value| value}.first
    end
    distances[end_town]
  end
end

router = Router.new(adjacency_matrix)

puts router.get_distance_of_route(["A","B","C"])
puts router.get_distance_of_route(["A","D"])
puts router.get_distance_of_route(["A","D","C"])
puts router.get_distance_of_route(["A","E","B","C","D"])
puts router.get_distance_of_route(["A","E","D"])
puts router.get_number_of_trips("C","C", 2, 3)
puts router.get_number_of_trips("A","C", 4, 4)
puts router.get_shortest_route_stack("A","C")
puts router.get_shortest_route_stack("B","B")
puts router.get_number_of_trips_distance("C","C", 29)